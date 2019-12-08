
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

                  // Per-key average using combineByKey() in Scala
val result = input.combineByKey(
(v) => (v, 1),
(acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1),
(acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
).map{ case (key, value) => (key, value._1 / value._2.toFloat) }
result.collectAsMap().map(println(_))

reduceByKey() with custom parallelism in Scala
val data = Seq(("a", 3), ("b", 4), ("a", 1))
sc.parallelize(data).reduceByKey((x, y) => x + y) // Default parallelism
sc.parallelize(data).reduceByKey((x, y) => x + y) // Custom parallelism

// check the size of the RDD
rdd.partitions.size()

// group data sharing the same key from multiple RDDs
x = sc.parallelize([("a", 1), ("b", 4)])
y = sc.parallelize([("a", 2)])
z = x.cogroup(y).collect()

// Scala shell inner join
storeAddress = {
    (Store("Ritual"), "1026 Valencia St"), (Store("Philz"), "748 Van Ness Ave"),
    (Store("Philz"), "3101 24th St"), (Store("Starbucks"), "Seattle")}

storeRating = {
(Store("Ritual"), 4.9), (Store("Philz"), 4.8))}

// Join
storeAddress.join(storeRating) == {
(Store("Ritual"), ("1026 Valencia St", 4.9)),
(Store("Philz"), ("748 Van Ness Ave", 4.8)),
(Store("Philz"), ("3101 24th St", 4.8))}

// leftOuterJoin() and rightOuterJoin()
storeAddress.leftOuterJoin(storeRating) ==
{(Store("Ritual"),("1026 Valencia St",Some(4.9))),
(Store("Starbucks"),("Seattle",None)),
(Store("Philz"),("748 Van Ness Ave",Some(4.8))),
(Store("Philz"),("3101 24th St",Some(4.8)))}
storeAddress.rightOuterJoin(storeRating) ==
{(Store("Ritual"),(Some("1026 Valencia St"),4.9)),
(Store("Philz"),(Some("748 Van Ness Ave"),4.8)),
(Store("Philz"), (Some("3101 24th St"),4.8))}

// Custom sort order in Scala, sorting integers as if strings
val input: RDD[(Int, Venue)] = ...
implicit val sortIntegersByString = new Ordering[Int] {
    override def compare(a: Int, b: Int) = a.toString.compare(b.toString)
}
rdd.sortByKey()

// Actions Available on Pair RDDs

// Count the number of elements for each key. 
rdd.countByKey()

// Collect the result as a map to provide easy lookup.
rdd.collectAsMap()

// Return all values associated with the provided key.
rdd.lookup(3)

// Data Partitioning (Advanced)
// Initialization code; we load the user info from a Hadoop SequenceFile on HDFS.
// This distributes elements of userData by the HDFS block where they are found,
// and doesn't provide Spark with any way of knowing in which partition a
// particular UserID is located.
val sc = new SparkContext(...)
val userData = sc.sequenceFile[UserID, UserInfo]("hdfs://...").persist()
// Function called periodically to process a logfile of events in the past 5 minutes;

// we assume that this is a SequenceFile containing (UserID, LinkInfo) pairs.
def processNewLogs(logFileName: String) {
    val events = sc.sequenceFile[UserID, LinkInfo](logFileName)
    val joined = userData.join(events)// RDD of (UserID, (UserInfo, LinkInfo)) pairs
    val offTopicVisits = joined.filter {
        case (userId, (userInfo, linkInfo)) => // Expand the tuple into its components
            !userInfo.topics.contains(linkInfo.topic)
    }.count()
    println("Number of visits to non-subscribed topics: " + offTopicVisits)
}

// Scala custom partitioner
val sc = new SparkContext(...)
val userData = sc.sequenceFile[UserID, UserInfo]("hdfs://...")
        .partitionBy(new HashPartitioner(100)) // Create 100 partitions
        .persist()

// Loading and saving your data
Loading a text file in Scala
val input = sc.textFile("file:///home/holden/repos/spark/README.md")                  

Average value per file in Scala
val input = sc.wholeTextFiles("file://home/holden/salesFiles") 
val result = input.mapValues{y =>
    val nums = y.split(" ").map(x => x.toDouble)
      nums.sum / nums.size.toDouble
}


// loading JSOn in Scala                  
case class Person(name: String, lovesPandas: Boolean) {
  // Must be a top-level class ...
  // Parse it into a specific case class. We use flatMap to handle errors
  // by returning an empty list (None) if we encounter an issue and a
  // list with one element if everything is ok (Some(_)).
  val result = input.flatMap(record => {
    try {
      Some(mapper.readValue(record, classOf[Person]))
    } catch {
      case e: Exception => None
    }
  })
}      

// Saving JSON in Scala
result.filter(p => P.lovesPandas).map(mapper.writeValueAsString(_)).saveAsTextFile(outputFile)

// Loading CSV with textFile() in Scala
import Java.io.StringReader
import au.com.bytecode.opencsv.CSVReader ...
val input = sc.textFile(inputFile)
val result = input.map{ line =>
    val reader = new CSVReader(new StringReader(line));
      reader.readNext();
}

# Loading CSV in full in Scala
case class Person(name: String, favoriteAnimal: String)
    val input = sc.wholeTextFiles(inputFile) val result = input.flatMap{ case (_, txt) =>
    val reader = new CSVReader(new StringReader(txt));
    reader.readAll().map(x => Person(x(0), x(1))) 
}

// Writing CSV in Scala
pandaLovers.map(person => List(person.name, person.favoriteAnimal).toArray).mapPartitions{people =>
    val stringWriter = new StringWriter();
    val csvWriter = new CSVWriter(stringWriter); csvWriter.writeAll(people.toList)
    Iterator(stringWriter.toString)
}.saveAsTextFile(outFile)                  
                  
// Loading a SequenceFile in Scala
val data = sc.sequenceFile(inFile, classOf[Text], classOf[IntWritable]). map{case (x, y) => (x.toString, y.get())}                  
                  
// Saving a SequenceFile in Scala
val data = sc.parallelize(List(("Panda", 3), ("Kay", 6), ("Snail", 2))) data.saveAsSequenceFile(outputFile)
                  
                  
                  
                  

                  
                  
                  
                  
                  
                  
                  
