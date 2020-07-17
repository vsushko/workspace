https://github.com/apache/kafka/blob/trunk/connect/file/src/main/java/org/apache/kafka/connect/file/FileStreamSourceConnector.java

str = "aabbcccd";
obj = "";
result = "";

for (let s of str) {
    if (obj[s]) {
        obj = {...obj, [s]: obj[s]  += 1 };
    } else {
        obj = {...obj, [s]: 1 };
    }
}

Object.keys(obj).forEach((key, index) => {
    if (obj[key] > 1) {
               result += key + obj[key];
    } else {
                result += key;
    }
});

console.log(result);
