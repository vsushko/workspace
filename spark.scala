
// Create an RDD called lines
val lines = sc.textFile("README.md") 

// Count the number of items in this RDD
lines.count() 

// First item in this RDD, i.e. first line of README.md
lines.first()

// Scala filtering example
val pythonLines = lines.filter(line => line.contains("Python"))
pythonLines.first()

// parallelize() method in Scala
val lines = sc.parallelize(List("pandas", "i like pandas"))

// filter() transformation in Scala
val inputRDD = sc.textFile("log.txt")
val errorsRDD = inputRDD.filter(line => line.contains("error"))

// Action
// Scala error count using actions
println("Input had " + badLinesRDD.count() + " concerning lines")
println("Here are 10 examples:")
badLinesRDD.take(10).foreach(println)

// Scala function passing
class SearchFunctions(val query: String) {
    def isMatch(s: String): Boolean = {
        s.contains(query)
    }
    def getMatchesFunctionReference(rdd: RDD[String]): RDD[String] = {
        // Problem: "isMatch" means "this.isMatch", so we pass all of "this"
        rdd.map(isMatch)
    }
    def getMatchesFieldReference(rdd: RDD[String]): RDD[String] = {
        // Problem: "query" means "this.query", so we pass all of "this"
        rdd.map(x => x.split(query))
    }
    def getMatchesNoReference(rdd: RDD[String]): RDD[String] = {
        // Safe: extract just the field we need into a local variable
        val query_ = this.query
        rdd.map(x => x.split(query_))
    }
}

// Common Transformations and Actions

// Scala squaring the values in an RDD
val input = sc.parallelize(List(1, 2, 3, 4))
val result = input.map(x => x * x)
println(result.collect().mkString(","))

// flatMap() in Scala, splitting lines into multiple words
val lines = sc.parallelize(List("hello world", "hi"))
val words = lines.flatMap(line => line.split(" "))
words.first() // returns "hello"

// Apply a function to each element in the RDD 
// and return an RDD of the result.
rdd.map(x => x + 1)

// Apply a function to each element in the RDD and return
// an RDD of the contents of the iterators returned. Often used to
// extract words.
rdd.flatMap(x => x.to(3))

// Return an RDD consisting of only elements that pass the condition
// passed to filter()
rdd.filter(x => x != 1)

// Remove duplicates.
rdd.distinct()

// Sample an RDD, with or without replacement.
rdd.sample(false, 0.5)

// Sample an RDD, with or without replacement.
rdd.union(other)

// RDD containing only elements found in both RDDs
rdd.intersection(other)

// Remove the contents of one RDD (e.g., remove training data).
rdd.subtract(other)

// Cartesian product with the other RDD.
rdd.cartesian(other)

// Actions

// reduce() in Scala
val sum = rdd.reduce((x, y) => x + y)

// aggregate() in Scala
val result = input.aggregate((0, 0))(
                (acc, value) => (acc._1 + value, acc._2 + 1),
                (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
val avg = result._1 / result._2.toDouble

// Basic actions on an RDD containing

// Return all elements from the RDD
rdd.collect()

// Number of elements in the RDD
 rdd.count()

// Number of times each element occurs in the RDD. 
rdd.countByValue()

// Return num elements from the RDD. 
rdd.take(2)

// Return the top num elements the RDD.
rdd.top(2)

// Return num elements based on provided ordering.
rdd.takeOrdered(2)(myOrdering)

// Return num elements at random. 
rdd.takeSample(false, 1)

// Combine the elements of the RDD together in parallel (e.g., sum ).
rdd.reduce((x, y) => x + y)

// Same as reduce() but with the provided zero value.
rdd.fold(0)((x, y) => x + y )

// Similar to reduce() but used to return a different type.
rdd.aggregate((0, 0))
((x, y) =>
(x._1 + y, x._2 + 1),
(x, y) =>
(x._1 + y._1, x._2 + y._2))

// Apply the provided function to each element of the RDD.
rdd.foreach(func)

// Persistence (caching)
val result = input.map(x => x * x)
result.persist(StorageLevel.DISK_ONLY)
println(result.count())
println(result.collect().mkString(","))

// Creating a pair RDD using the first word as the key in Scala
val pairs = lines.map(x => (x.split(" ")(0), x))

// Transformations on Pair RDDs

// Combine values with the same key.
rdd = rdd.reduceByKey(lambda x, y: x + y)

// Group values with the same key.
rdd.groupByKey()

// Combine values with the same key using a different result type
// TODO

// Apply a function to each value of a pair
// RDD without changing the key.
rdd.mapValues(x => x + 1)

// Apply a function that returns an iterator to each value of a pair
// RDD, and for each element returned, produce a key/value
// entry with the old key. Often used for tokenization.
rdd.flatMapValues(x => (x to 5)

// Return an RDD of just the keys.
rdd.keys()

// Return an RDD of just the values.
rdd.values()

// Return an RDD sorted by the key.
rdd.sortByKey()

// Transformations on two pair RDDs
rdd1 = sc.parallelize([(1, 2), (3, 4), (3, 6)])
rdd2 = sc.parallelize([(3, 9)])

// Remove elements with a key present in the other RDD.
rdd1.subtractByKey(rdd2)

// Perform an inner join between two RDDs.
rdd1.join(rdd2)

// Perform a join between two RDDs where the key must
// be present in the first RDD.
rdd1.rightOuterJoin(rdd2)

// Perform a join between two RDDs where the key must
// be present in the other RDD.
rdd1.leftOuterJoin(rdd2)

// Group data from both RDDs sharing the same key.
rdd1.cogroup(rdd2)

// Simple filter on second element in Python
pairs.filter{case (key, value) => value.length < 20}

// Aggregations

// Per-key average with reduceByKey() and mapValues() in Scala
rdd.mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))

// Word count in Scala
val input = sc.textFile("s3://...")
val words = input.flatMap(x => x.split(" "))
val result = words.map(x => (x, 1)).reduceByKey((x, y) => x + y)
