# Web crawler
Web crawler collects statistics about the number of given terms on visited pages.  User defines the seed (start URL), link depth (8 by default), max visited pages limit (10000 by default), and the terms which should be counted.  The output contains all visited pages and numbers of occurrences of each term on every page.  All data is serialized into a CSV file; top 10 pages by total hits are printed to another CSV file and console sorted by total hits.  

## Example
Seed: https://en.wikipedia.org/wiki/Elon_Musk
Terms: Tesla, Musk, Gigafactory, Elon Mask
Output:
https://en.wikipedia.org/wiki/Elon_Musk 208 641 9 0
acbd.com/page2.html 8 4 0 5 17
qwerty.com/page1.html 3 2 0 2 7
anothersite.com/page3.html 0 1 0 1 2

## Sample data
