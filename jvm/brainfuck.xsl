<?xml version="1.0" encoding="iso-8859-1"?>

<!--
  brainfuck.xslt
  Copyright (C) 2004 Jörgen Cederlöf <jc@lysator.liu.se>

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License
  as published by the Free Software Foundation; either version 2
  of the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
-->

<!--
  This stylesheet transforms a brainfuck
  (http://en.wikipedia.org/wiki/Brainfuck) xml document to an output
  brainfuck xml document, by running the Brainfuck program in the
  /brainfuck/code element with /brainfuck/input as input. Be sure to
  escape the brainfuck statements properly as XML requires. Characters
  in the code element which are not brainfuck instructions are
  ignored.

  No xslt extensions are used, just plain xslt 1.0.

  The array is dynamically resized on the right (>) side when needed
  and can be considered to be of infinite size. To see the array for
  every statement processed, pass parameter "debug" with a value
  evaluating to true() to the stylesheet.

  Be aware that, since this stylesheet works with recursion, some xslt
  processors will eat lots of memory and/or segfault when running
  large complicated Brainfuck programs.

  For example, the documents:

  <?xml version="1.0" encoding="iso-8859-1"?>
  <brainfuck>
    <code>,[.,]</code>
    <input>Hello World!</input>
  </brainfuck>

  and: (The extra spaces are just to make this a valid XML comment.)

  <?xml version="1.0" encoding="iso-8859-1"?>
  <brainfuck>
    <code><![CDATA[
      ++++++++++[>+++++++>++++++++++>+++>+<<<<-]
      >++.>+.+++++++..+++.>++.<<+++++++++++++++.
      >.+++.- - - - - -.- - - - - - - -.>+.
      ]]></code>
  </brainfuck>

  both produce the document:

  <?xml version="1.0" encoding="iso-8859-1"?>
  <brainfuck>
    <result>Hello World!</result>
  </brainfuck>

  The document:

  <?xml version="1.0" encoding="iso-8859-1"?>
  <brainfuck>
    <code><![CDATA[
      ,>,,>++++++++[<- - - - - -<- - - - - ->>-]
      <<[>[>+>+<<-]>>[<<+>>-]<<<-]
      >>>++++++[<++++++++>-],<.>.
      ]]></code>
    <input>2*3</input>
  </brainfuck>

  will produce:

  <?xml version="1.0" encoding="iso-8859-1"?>
  <brainfuck>
    <result>6</result>
  </brainfuck>

  This stylesheet is available at
  http://www.lysator.liu.se/~jc/hacks/brainfuck.xslt

-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output
    method="xml"
    version="1.0"
    encoding="iso-8859-1"
    omit-xml-declaration="no"
    indent="yes"
    />

  <xsl:param name="debug" select="false()"/>

  <xsl:template match="/brainfuck">
    <xsl:variable name="code">
      <xsl:call-template name="preprocess">
        <xsl:with-param name="code" select="normalize-space(code)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="$debug">
      <xsl:message>Code: <xsl:value-of select="$code"/></xsl:message>
    </xsl:if>
    
    <brainfuck>
      <result>
        <xsl:call-template name="process">
          <xsl:with-param name="code"  select='$code'  />
          <xsl:with-param name="input" select='input' />
        </xsl:call-template>
      </result>
    </brainfuck>
  </xsl:template>

  <!-- Remove junk characters and calculate loop lengths -->
  <xsl:template name="preprocess">
    <xsl:param name="code"/>
    <xsl:variable name="statement" select="substring($code, 1, 1)"/>

    <xsl:if test='contains("&lt;&gt;+-.,", $statement)'>
      <xsl:value-of select="$statement"/>
    </xsl:if>

    <xsl:if test='$statement = "["'>
      <xsl:variable name="looplen">
        <xsl:call-template name="getlooplength">
          <xsl:with-param name="code" select="substring($code, 2)"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:variable name="loop">
        <xsl:call-template name="preprocess">
          <xsl:with-param name="code" select='substring($code, 2, $looplen)'  />
        </xsl:call-template>
      </xsl:variable>
        
      <xsl:value-of select="concat('[', string-length($loop), ' ', $loop,
                                   ']', string-length($loop), ' ')"/>
      <xsl:call-template name="preprocess">
        <xsl:with-param name="code" select='substring($code, $looplen+3)'  />
      </xsl:call-template>
    </xsl:if>
    
    <xsl:if test="$statement and $statement != '['">
      <xsl:call-template name="preprocess">
        <xsl:with-param name="code" select='substring($code, 2)'  />
      </xsl:call-template>
    </xsl:if>
  </xsl:template>

  <!-- Find the matching ] -->
  <xsl:template name="getlooplength">
    <xsl:param name="code"/>
    <xsl:variable name="loopcandidate" select='substring-before($code, "]")'/>

    <xsl:choose>
      <xsl:when test='contains($loopcandidate, "[")'>
        <xsl:variable name="beforelen"
          select='string-length(substring-before($code, "["))'/>
        <xsl:variable name="nextlen">
          <xsl:call-template name="getlooplength">
            <xsl:with-param name="code" select='substring-after($code, "[")'/>
          </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="afterlen">
          <xsl:call-template name="getlooplength">
            <xsl:with-param name="code" select='substring($code, 1+$beforelen+1+$nextlen+1)'/>
          </xsl:call-template>
        </xsl:variable>
        <xsl:value-of select='$beforelen+1+$nextlen+1+$afterlen'/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select='string-length($loopcandidate)'/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- Run the code -->
  <xsl:template name="process">
    <xsl:param name="code"/>
    <xsl:param name="input"/>
    <xsl:param name="array"    select='" 000"'/>
    <xsl:param name="arraypos" select='0'/>
    <xsl:param name="codepos"  select='1'/>

    <xsl:variable name="value"     select="number(substring($array, $arraypos+2, 3))"/>
    <xsl:variable name="statement" select="substring($code, $codepos, 1)"/>

    <xsl:if test="$debug">
      <xsl:message>
        <xsl:choose>
          <xsl:when test="$statement">
            <xsl:value-of select='concat("Processing: ", $statement, " ", $array, " :", $arraypos div 4)'/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select='concat("End:          ", $array, " :", $arraypos div 4)'/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:message>
    </xsl:if>
    
    <!-- Increase or decrease pointer -->
    <xsl:if test='($statement = "&gt;") or ($statement = "&lt;")'>
      <xsl:variable name="newarraypos" select='$arraypos+4 - 8*($statement="&lt;")'/>
      <xsl:variable name="newarray">
        <xsl:choose>
          <xsl:when test="$newarraypos &gt;= string-length($array)">
            <xsl:value-of select='concat($array, " 000")'/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select='$array'/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:call-template name="process">
        <xsl:with-param name="code"     select='$code'  />
        <xsl:with-param name="codepos"  select='$codepos+1' />
        <xsl:with-param name="input"    select='$input' />
        <xsl:with-param name="array"    select='$newarray' />
        <xsl:with-param name="arraypos" select='$newarraypos' />
      </xsl:call-template>
    </xsl:if>

    <!-- Increase or decrease byte at pointer -->
    <xsl:if test='($statement = "+") or ($statement = "-")'>
      <xsl:variable name="newvalue">
        <xsl:choose>
          <xsl:when test='$statement = "+"'>
            <xsl:value-of select="($value +   1) mod 256"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="($value + 255) mod 256"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      <xsl:variable name="newstr">
        <xsl:choose>
          <xsl:when test="$newvalue != 0">
            <xsl:number format=" 001" value="$newvalue"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select='" 000"'/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      <xsl:call-template name="process">
        <xsl:with-param name="code"     select='$code'  />
        <xsl:with-param name="codepos"  select='$codepos+1' />
        <xsl:with-param name="input" select='$input' />
        <xsl:with-param name="array" select='concat(substring($array, 1, $arraypos),
                                                    $newstr,
                                                    substring($array, $arraypos+5))' />
        <xsl:with-param name="arraypos" select='$arraypos' />
      </xsl:call-template>
    </xsl:if>
    
    <!--  Output from or input to the byte at the pointer (ASCII). -->
    <xsl:if test='($statement = ".") or ($statement = ",")'>
      <xsl:variable name="ascii0" select='"        &#9;&#10;  &#13;                  "'/>
      <xsl:variable name="ascii1"> !&#34;#$%&amp;&#39;()*+,-./0123456789:;&lt;=&gt;?</xsl:variable>
      <xsl:variable name="ascii2" select='"@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_"'/>
      <xsl:variable name="ascii3" select='"`abcdefghijklmnopqrstuvwxyz{|}~&#127;"'/>
      <!-- Fill out with the rest of the 128-255 characters -->
      <xsl:variable name="ascii4" select='"&#x80;&#x81;&#x82;&#x83;&#x84;&#x85;&#x86;&#x87;&#x88;&#x89;&#x8a;&#x8b;&#x8c;&#x8d;&#x8e;&#x8f;&#x90;&#x91;&#x92;&#x93;&#x94;&#x95;&#x96;&#x97;&#x98;&#x99;&#x9a;&#x9b;&#x9c;&#x9d;&#x9e;&#x9f;&#xa0;&#xa1;&#xa2;&#xa3;&#xa4;&#xa5;&#xa6;&#xa7;&#xa8;&#xa9;&#xaa;&#xab;&#xac;&#xad;&#xae;&#xaf;&#xb0;&#xb1;&#xb2;&#xb3;&#xb4;&#xb5;&#xb6;&#xb7;&#xb8;&#xb9;&#xba;&#xbb;&#xbc;&#xbd;&#xbe;&#xbf;&#xc0;&#xc1;&#xc2;&#xc3;&#xc4;&#xc5;&#xc6;&#xc7;&#xc8;&#xc9;&#xca;&#xcb;&#xcc;&#xcd;&#xce;&#xcf;&#xd0;&#xd1;&#xd2;&#xd3;&#xd4;&#xd5;&#xd6;&#xd7;&#xd8;&#xd9;&#xda;&#xdb;&#xdc;&#xdd;&#xde;&#xdf;&#xe0;&#xe1;&#xe2;&#xe3;&#xe4;&#xe5;&#xe6;&#xe7;&#xe8;&#xe9;&#xea;&#xeb;&#xec;&#xed;&#xee;&#xef;&#xf0;&#xf1;&#xf2;&#xf3;&#xf4;&#xf5;&#xf6;&#xf7;&#xf8;&#xf9;&#xfa;&#xfb;&#xfc;&#xfd;&#xfe;"'/>
      <xsl:variable name="ascii"  select="concat($ascii0, $ascii1, $ascii2, $ascii3, $ascii4)"/>

      <xsl:choose>
        <xsl:when test='$statement = "."'>
          <xsl:if test="$value != 0">
            <xsl:value-of select='substring($ascii, $value, 1)' />
          </xsl:if>
          <xsl:call-template name="process">
            <xsl:with-param name="code"     select='$code'  />
            <xsl:with-param name="codepos"  select='$codepos+1' />
            <xsl:with-param name="input"    select='$input' />
            <xsl:with-param name="array"    select='$array' />
            <xsl:with-param name="arraypos" select='$arraypos' />
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:variable name="newvalue">
            <xsl:if test='substring($input, 1, 1)=" "'>
              <xsl:value-of select='" 032"'/>
            </xsl:if>
            <xsl:if test='substring($input, 1, 1)=""'>
              <xsl:value-of select='" 000"'/>
            </xsl:if>
            <xsl:if test='substring($input, 1, 1)!="" and
                          substring($input, 1, 1)!=" "'>
              <xsl:number format=" 001"
                value="string-length(substring-before($ascii, substring($input, 1, 1)))+1"/>
            </xsl:if>
          </xsl:variable>
          <xsl:call-template name="process">
            <xsl:with-param name="code"     select='$code'  />
            <xsl:with-param name="codepos"  select='$codepos+1' />
            <xsl:with-param name="input"   select='substring($input, 2)' />
            <xsl:with-param name="array" select='concat(substring($array, 1, $arraypos),
                                                        $newvalue,
                                                        substring($array, $arraypos+5))' />
            <xsl:with-param name="arraypos" select='$arraypos' />
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <!-- Loop -->
    <xsl:if test='($statement = "[" or $statement = "]")'>
      <xsl:variable name="looplen"
        select='substring-before(substring($code, $codepos+1), " ")'/>

      <xsl:variable name="offset">
        <xsl:choose>
          <xsl:when test='(    $value  and $statement = "[") or
                          (not($value) and $statement = "]")'>
            <xsl:value-of select="string-length($looplen)+2"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select='(string-length($looplen)+2)*2*($statement="[") +
                                  number($looplen)*(($statement="[")*2-1)'/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      <xsl:call-template name="process">
        <xsl:with-param name="code"     select='$code'  />
        <xsl:with-param name="codepos"  select='$codepos + $offset' />
        <xsl:with-param name="input"    select='$input' />
        <xsl:with-param name="array"    select='$array' />
        <xsl:with-param name="arraypos" select='$arraypos' />
      </xsl:call-template>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
