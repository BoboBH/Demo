import sys;

list = [1,2,3,4,5];
it = iter(list);
print("try to go through iteration...");
while True:
    try:
        print(next(it));
    except StopIteration:
        break;
print("went through all element iteration");
