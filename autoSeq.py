import subprocess

inFiles = ["./Data/large_in.txt", "./Data/med_in.txt", "./Data/small_in.txt"]
outFiles = ["basinTimes_large.txt", "basinTimes_med.txt", "basinTimes_small.txt"]


for i in range(3):
    average = float(0)

    output = subprocess.run(["java", "basins", inFiles[i]], stdout = subprocess.PIPE, text = True)
    capOut = output.stdout
    
    outLines = capOut.split("\n")
    print(outLines)
    

   # for j in range(1,11):
        #length = len(outLines[j])
        #start = outLines[j].find(":")+1
        #convert = outLines[j][start+1:length]
        #print(convert)
        #average += float(convert)

    with open(outFiles[i],"a+")as f:
        f.writelines(capOut)
        #f.writelines("Average: " + str(average/10))