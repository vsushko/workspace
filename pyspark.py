# learning spark

# Create an RDD called lines
lines = sc.textFile("README.md")

# Count the number of items in this RDD
lines.count()

# First item in this RDD, i.e. first line of README.md
lines.first() 

# Python filtering example
pythonLines = lines.filter(lambda line: "Python" in line)
pythonLines.first()

# parallelize() method in Python
lines = sc.parallelize(["pandas", "i like pandas"])

# Transformations

# filter() transformation in Python
inputRDD = sc.textFile("README.md")
errorsRDD = inputRDD.filter(lambda x: "error" in x)

errorsRDD = inputRDD.filter(lambda x: "error" in x)
warningsRDD = inputRDD.filter(lambda x: "warning" in x)
badLinesRDD = errorsRDD.union(warningsRDD)

# Action

# Python error count using actions
print "Input had " + badLinesRDD.count() + " concerning lines"
print "Here are 10 examples:"
for line in badLinesRDD.take(10):
    print line

# Passing functions in Python
word = rdd.filter(lambda s: "error" in s)
def containsError(s):
    return "error" in s
word = rdd.filter(containsError)

# Python function passing without field references
class WordFunctions(object):
    ...
    def getMatchesNoReference(self, rdd):
        # Safe: extract only the field we need into a local variable
        query = self.query
        return rdd.filter(lambda x: query in x)


# Common Transformations and Actions

# Python squaring the values in an RDD
nums = sc.parallelize([1, 2, 3, 4])
squared = nums.map(lambda x: x * x).collect()
for num in squared:
    print("%i " % (num))

# flatMap() in Python, splitting lines into words
lines = sc.parallelize(["hello world", "hi"])
words = lines.flatMap(lambda line: line.split(" "))
words.first() # returns "hello"

# Basic RDD transformations on an RDD

# Apply a function to each element in the RDD 
# and return an RDD of the result.
nums = nums.map(lambda x:  x + 1)

# Apply a function to each element in the RDD and return
# an RDD of the contents of the iterators returned. Often used to
# extract words.

# TODO: find alternative in python
# nums = nums.flatMap(lambda x: x.to(3))

# Return an RDD consisting of only elements that pass the condition
# passed to filter()
nums = nums.filter(lambda x: x != 1)

# Remove duplicates.
nums = nums.distinct()

# Sample an RDD, with or without replacement.
nums = nums.sample(false, 0.5)

# Produce an RDD containing elements from both RDDs
nums = nums.union(other)

# Sample an RDD, with or without replacement.
nums1 = sc.parallelize([1,2,3])
nums2 = sc.parallelize([3,4,5])
nums = nums1.union(nums2)
print(nums.collect())

# RDD containing only elements found in both RDDs
nums = nums1.intersection(nums2)

# Remove the contents of one RDD (e.g., remove training data).
nums = nums1.subtract(nums2)

# Cartesian product with the other RDD.
nums = nums1.cartesian(num2)

# Actions

nums = sc.parallelize([1,2,3,4,5,6,7,8,9,10])

# reduce() in Python
sum = nums.reduce(lambda x, y: x + y)
print(sum)

# aggregate() in Python
sumCount = nums.aggregate((0, 0),
                (lambda acc, value: (acc[0] + value, acc[1] + 1),
                (lambda acc1, acc2: (acc1[0] + acc2[0], acc1[1] + acc2[1]))))
return sumCount[0] / float(sumCount[1])

# Basic actions on an RDD containing
nums = sc.parallelize([1,2,3,4,5,6,7,8,9,10])

# Return all elements from the RDD
nums.collect()

# Number of elements in the RDD
nums.count()

# Number of times each element occurs in the RDD. 
nums.countByValue()

# Return num elements from the RDD. 
nums.take(2)

# Return the top num elements the RDD.
nums.top(2)

# Return num elements based on provided ordering.
nums.takeOrdered(2)(myOrdering)

# Return num elements at random.
nums.takeSample(False, 1)

# Combine the elements of the RDD together in parallel (e.g., sum ).
nums.reduce(lambda x, y: x + y)

# Same as reduce() but with the provided zero value.
nums.fold(0, lambda x, y: x + y)

# Similar to reduce() but used to return a different type.
nums.aggregate((0, 0)) ((x, y) => 
    (x._1 + y, x._2 + 1), (x, y) => (x._1 + y._1, x._2 + y._2))

# Apply the provided function to each element of the RDD.
nums.foreach(func)

# Persistence (caching)
import pyspark
nums = sc.parallelize([1,2])
nums.persist(pyspark.StorageLevel.MEMORY_AND_DISK_2)
nums.getStorageLevel()
print(nums.getStorageLevel())

result = nums.map(lambda x: x * x)
result.persist(StorageLevel.DISK_ONLY)
print(result.count())
print(result.collect().mkString(","))

# Creating a pair RDD using the first word as the key in Python
pairs = lines.map(lambda x: (x.split(" ")[0], x))

# Transformations on Pair RDDs
rdd = sc.parallelize([(1, 2), (3, 4), (3, 6)])

# Combine values with the same key.
rdd = rdd.reduceByKey(lambda x, y: x + y)

# Group values with the same key.
rdd.groupByKey()

# Combine values with the same key using a different result type
# TODO

# Apply a function to each value of a pair
# RDD without changing the key.
rdd = rdd.mapValues(lambda x: x + 1)

# Apply a function that returns an iterator to each value of a pair
# RDD, and for each element returned, produce a key/value
# entry with the old key. Often used for tokenization.
rdd = rdd.flatMapValues(lambda x: range(x, 6))

# Return an RDD of just the keys.
rdd.keys()

# Return an RDD of just the values.
rdd.values()

# Return an RDD sorted by the key.
rdd.sortByKey()

# Transformations on two pair RDDs
rdd1 = sc.parallelize([(1, 2), (3, 4), (3, 6)])
rdd2 = sc.parallelize([(3, 9)])

# Remove elements with a key present in the other RDD.
rdd1.subtractByKey(rdd2)

# Perform an inner join between two RDDs.
rdd1.join(rdd2)

# Perform a join between two RDDs where the key must
# be present in the first RDD.
rdd1.rightOuterJoin(rdd2)

# Perform a join between two RDDs where the key must
# be present in the other RDD.
rdd1.leftOuterJoin(rdd2)

# Group data from both RDDs sharing the same key.
rdd1.cogroup(rdd2)

# Simple filter on second element in Python
result = pairs.filter(lambda keyValue: len(keyValue[1]) < 20)

# Aggregations

# Per-key average with reduceByKey() and mapValues() in Python
rdd.mapValues(lambda x: (x, 1)).reduceByKey(lambda x, y: (x[0] + y[0], x[1] + y[1]))

# Word count in Python
rdd = sc.textFile("s3://...")
words = rdd.flatMap(lambda x: x.split(" "))
result = words.map(lambda x: (x, 1)).reduceByKey(lambda x, y: x + y)
