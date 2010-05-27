# maybe people don't get so fast so fat ,so lets add only 0.5 to the weight 
# test it
# then try to enter a weight of 60.7
# what happens? try to fix it by looking at the height code
# in the end you should get weight of 61.2 if you enter 60.7,


weight = int(raw_input("Enter your weight: "))
weight = weight + 2
print "In one year you are",weight,"kg heavy."


height = float(raw_input("Enter your height: "))
height = height + 0.1
print "In one year you are",height,"meter high."

