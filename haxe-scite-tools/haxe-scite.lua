function make_uppercase()
  local sel = editor:GetSelText()
  editor:ReplaceSel(string.upper(sel))
end

function os.capture(cmd, raw)
  local f = assert(io.popen(cmd, 'r'))
  local s = assert(f:read('*a'))
  f:close()
  if raw then return s end
  s = string.gsub(s, '^%s+', '')
  s = string.gsub(s, '%s+$', '')
  s = string.gsub(s, '[\n\r]+', ' ')
  return s
end

function OnSave(s)  
	if (string.find(s,".hxx")) then
		print "Compilling"
		print (os.capture("haxe compile.hxml 2>&1"))
	end
	return false
end

function showCodeComplete()
	scite.MenuCommand(IDM_SAVE)
	pos = editor.CurrentPos
	filename=props['FilePath']
	className = props['FileName']
	path = string.sub(filename,0,filename:len()-3-className:len())
	
	--filename = props['FileName']..'.'..props['FileExt']
	commandString = 'haxe -cp '..path..' -main '..className..' --display '..filename..'@'..pos.." -swf9 t.swf --no-output 2>&1"
	print (commandString)
	--print('cd '..path)
	--os.execute('cd '..path)
	--print(os.capture('ls'))
	commandOutput= os.capture(commandString)
	print(commandOutput)
	codeCompletionItems =  getCodeCompletionItems(commandOutput)
	if #(codeCompletionItems)>0 then
		showUserList(codeCompletionItems);
	end
end



function getCodeCompletionItems(displayXml) 
	items = {}
	for name,args in string.gmatch(displayXml, "<i n=\"(%S*)\".-<t>(.-)</t>") do
		displayString = name;
		if #args>0 then
			argsText = args:gsub("\n",""):gsub("&gt;",">"):gsub("&lt;","<"):gsub("?","")
			displayString = displayString.." ("..argsText..")"
		end
		table.insert(items,displayString);
	end
	return items
end

t = getCodeCompletionItems("<list> <i n=\"Boot\"><t>tol\nl</t><d><list> <i n=\"bla\"><t></t><d>") 
for i,v in ipairs(t) do
	-- print(v)
end


 function showUserList(list)
   local s = ''
   local sep = '�'
   local n = table.getn(list)
   for i = 1,n-1 do
      s = s..list[i]..sep
   end
   s = s..list[n]
   editor.AutoCSeparator = string.byte(sep)
   editor:UserListShow(12,s)
   editor.AutoCSeparator = string.byte(' ')
 end
 
 function OnUserListSelection(tp,item)
   if tp == 12 then 
	insertText = item:gsub(" (.*","")
	pos = editor.CurrentPos
	editor:insert(pos,insertText)
	editor:GotoPos(pos+#insertText)
   end
 end

function OnKey(key,m,strg,alt) 
	if props['FileExt']=="hx" then
		if strg then		
			if key == 32 then			
				showCodeComplete();
			end
		end 
	end
	return false
end

function formatSourceCode(filename)
	pos = editor.CurrentPos
	captured = os.capture("uncrustify -c uncrustify.cfg -f "..filename.." -o temp 2>&1")
	print(captured)
	file = io.open("temp");
	print (editor:SetText(file:read("*a")))
	file:close()
	scite.MenuCommand(IDM_SAVE)
	editor:GotoPos(pos)
end