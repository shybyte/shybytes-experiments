# write a program that calculates the area and the perimeter of a rectangle 
# using a function

# this defines a function, pay attention the the indentation
def squareArea(x):
    area = x*x 
    return area

a = float(raw_input("Enter Square side length : "))
area = squareArea(a)
print "Area =",area

