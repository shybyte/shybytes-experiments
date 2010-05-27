# add teenager without using elif !
# pay attention to the double indentation 

age = int(raw_input("Enter your age: "))

if age<3:
    print "You are a baby."
else:
    print "You are no baby anymore"
    if age<20:
        print "You are a kid."
        print "Or a bigger kid."
    else:
        print "You are an adult."
    




