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
