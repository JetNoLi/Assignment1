import subprocess


##To find sequential cutoff
inFiles = ["./Data/large_in.txt", "./Data/med_in.txt", "./Data/small_in.txt"]
outFiles = ["paraBasinTimes_large.txt", "paraBasinTimes_med.txt", "paraBasinTimes_small.txt"]
programs = ["paraBasinRun","paraBasins.java"]

for i in range(3):

    average = 0

    if (i == 0):
        prev = 1

    sequential_cutoff = 2
    num = 2

    while(sequential_cutoff <= 2**14):
        
        lines = open(programs[1],"r").readlines()
        change = lines
        pos = -1

        for line in lines:
            pos+=1

            if "//replacable" in line:
                change[pos] = line.replace(str(prev),str(sequential_cutoff))
    
        with open(programs[1], "w") as f:
            f.writelines(change)

        subprocess.run(["javac",programs[1]])    
        #print(inFiles[i])
        output = subprocess.run(["java",programs[0],inFiles[i],str(sequential_cutoff)],stdout= subprocess.PIPE, text = True)
        capOut = (output.stdout)
        #outLines = capOut.split("\n")

        #for j in range(1,11):
            #average += float(outLines[j])

        prev = sequential_cutoff
        sequential_cutoff = 2**num
        num += 1

        with open(outFiles[i],"a+") as f:
            f.writelines(capOut)
            #f.writelines("Average:" + str(average/10))



