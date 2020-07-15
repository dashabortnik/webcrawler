# Web crawler
Web crawler collects statistics about the number of given terms on visited pages. User defines the seed (start URL), link depth (8 by default), max visited pages limit (10000 by default), and the terms which should be counted. The output contains all visited pages and numbers of occurrences of each term on every page. All data is serialized into a CSV file; top 10 pages by total hits are printed to another CSV file and console sorted by total hits.  

## Example
Seed: https://en.wikipedia.org/wiki/Elon_Musk  
Terms: Tesla, Musk, Gigafactory, Elon Mask  
Output:  
https://en.wikipedia.org/wiki/Elon_Musk 208 641 9 0  
acbd.com/page2.html 8 4 0 5 17  
qwerty.com/page1.html 3 2 0 2 7  
anothersite.com/page3.html 0 1 0 1 2  

## Sample data
[{  
  "seed": "https://stackoverflow.com/",  
	"linkDepth": 2,  
	"maxPagesLimit": 4,  
	"searchTermsString": "java, tutorial, com"  
},  
 {  
  	"seed": "https://www.onliner.by",  
  	"linkDepth": 3,  
  	"maxPagesLimit": 5,  
	"searchTermsString": "товар, сегодня"  
}  
]  

## Installation
1. Clone the project from repository. 
2. Change paths in properties files:  
   2.1. File "src\main\resources\constant.properties"  
        2.1.1. file.output.name.no.ext - default path (used if no other is specified)  
        2.1.2. Set necessary output parameters with correct paths in section "#output parameters".  
   2.2. File "src\test\resources\test.properties"  
      	Set parameters "filepath1" and "filepath2" as desired output directories with filenames.  
3. Prepare output parameters by copying sample data above into a .txt file or taking a file inputDataArray.txt from "src\test\resources".   
4. Open cmd in project root directory.  
5. Run one of the following scripts:  
- for input of args from command line  
crawl.sh  
- for input of args from JSON file  
crawl.sh [filepath]  
