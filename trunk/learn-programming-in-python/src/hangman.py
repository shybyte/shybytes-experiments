import random

def readLinesFromFile(filename):
    file = open(filename)
    words = file.readlines()
    file.close
    return words

def getWordMask(word,guessedLetters):
    wordMaskWithSpaces = ''
    for letter in word:
        if letter in guessedLetters:
            wordMaskWithSpaces += letter
        else:
            wordMaskWithSpaces += '_'
        wordMaskWithSpaces += ' '
    return wordMaskWithSpaces

def countDifferentLettersInWord(word):
    foundLetters = []
    for letter in word:
        if letter not in foundLetters:
            foundLetters.append(letter)
    return len(foundLetters)
            

lines = readLinesFromFile('words.txt')
word = random.choice(lines).strip()

guessedLetters = []
lettersToGuess = countDifferentLettersInWord(word)
errors = 0

while lettersToGuess > 0:
    print getWordMask(word,guessedLetters)
    
    inputLetter = raw_input("Guess a letter: ")
    
    if inputLetter in guessedLetters:
        print "You have already guessed this letter!"
    else:
        guessedLetters.append(inputLetter)
        if inputLetter in word:
            print "Right!"
            lettersToGuess -= 1
        else:
            print "Wrong!"
            errors += 1
            
print "You made ",errors,"errors."

        
    
        
    