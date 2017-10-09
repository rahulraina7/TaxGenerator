# TaxGenerator

Program to generate Sales Tax on a list of items.


Sales Tax - 10%

Import Duty - 5%

Current Assumptions : 

1) Any item whose product name contains 'imported' will incur Import Duty
2) Any item whose product name contains 'chocolate' or 'food', means its of type food and will not incur Sales Tax
3) Any item whose product name contains 'book', means its of type books and will not incur Sales Tax
4) Any item whose product name contains 'medical' or 'medicine' or 'pills', means its of type medical products and will not incur Sales Tax





**Usage Instructions:**

Tests are run before each program execution. Please refer to ```mvn_build.log``` after execution.


To read input from csv file and print to standard out use : 
```
./TaxGenerator <input_csv_file> true
```
To read input from csv file and print to csv file use :

```
./TaxGenerator <input_csv_file>
```
To read from standard input and print to standard output use : 
```
./TaxGenerator
<Paste CSV Contents>
```


