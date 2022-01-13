Spring Batch meta-data schema:
```
https://docs.spring.io/spring-batch/docs/current/reference/html/schema-appendix.html
```
clear batch_* meta-data data:
```sql
DELETE FROM BATCH_STEP_EXECUTION_CONTEXT WHERE STEP_EXECUTION_ID IN (SELECT BATCH_STEP_EXECUTION.STEP_EXECUTION_ID FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION WHERE STATUS IN ('COMPLETED','FAILED')));
DELETE FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION WHERE STATUS IN ('COMPLETED','FAILED'));
DELETE FROM BATCH_JOB_EXECUTION_CONTEXT WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION WHERE STATUS IN ('COMPLETED','FAILED'));
DELETE FROM BATCH_JOB_EXECUTION_PARAMS WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION WHERE STATUS IN ('COMPLETED','FAILED'));
DELETE FROM BATCH_JOB_EXECUTION WHERE STATUS IN ('COMPLETED','FAILED');
DELETE FROM BATCH_JOB_INSTANCE WHERE JOB_INSTANCE_ID NOT IN (SELECT BATCH_JOB_EXECUTION.JOB_INSTANCE_ID FROM BATCH_JOB_EXECUTION);
```

Pass File name as Parameter and Use StepScope (could be passed via idea program arguments):
```
start_dt=2020-09 fileInput=input/product.csv fileOutput=output/product_out.csv
```
code:
```java
@StepScope
@Bean
public FlatFileItemReader reader(@Value("#{jobParameters['fileInput']}") FileSystemResource inputFile) {
    FlatFileItemReader reader = new FlatFileItemReader();
    reader.setResource(inputFile);
    reader.setLinesToSkip(1);
    reader.setLineMapper(new DefaultLineMapper() {
        {
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {
                {
                    setTargetType(Product.class);
                }
            });
            setLineTokenizer(new DelimitedLineTokenizer() {
                {
                    setNames("productId", "productName", "productDesc", "price", "unit");
                    setDelimiter(",");
                }
            });
        }
    });
    return reader;
}

@StepScope
@Bean
public FlatFileItemWriter flatFileItemWriter(@Value("#{jobParameters['fileOutput']}") FileSystemResource outputFile) {
    FlatFileItemWriter writer = new FlatFileItemWriter();
    writer.setResource(outputFile);
    writer.setLineAggregator(new DelimitedLineAggregator() {
        {
            setDelimiter("|");
            setFieldExtractor(new BeanWrapperFieldExtractor() {
                {
                    setNames(new String[]{"productId", "productName", "productDesc", "price", "unit"});
                }
            });
        }
    });
    return writer;
}

@Bean
public Step step1() {
    return steps.get("step1")
            .<Product, Product>chunk(3)
            .reader(reader(null))
            .writer(flatFileItemWriter(null))
            .build();
}

@Bean
public Job job1() {
    return jobs.get("job1")
            .start(step1())
            .build();
}
```
