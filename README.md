# Demo for updating mongodb

## Prerequisites:
1. Building up docker through docker-compose
2. Visiting `mydatabase/table1`. If there is no mydatabase, mongostore can recover the database:
   
   ``` docker exec -it hongwei-mongo /bin/bash ```
    
   ``` mongorestore --host=127.0.0.1 --port=27017 --username=mongoadmin --authenticationDatabase=admin --archive=/home/mongodb.archive --db=mydatabase ```
3. Using follow command, we can get access to mongodb-client

    ``` docker exec -it hongwei-mongo mongosh ```

4. Receiving the josn file format is as follow: {vin: valueVin, field1: valueField1, field2: valueField2: field3: valueField4}
## Only updating field1
The update process is facilitated through the `VinInfoRepository` interface. To perform the update in MongoDB, the `@Update` tag is required. An additional method, `updateVinInfoByVinJustQuery`, serves as evidence that using only `@Query` does not suffice.
The specific instructions and comments for this process are documented in the `VinInfoRepository`.

``` 
    @Query("{'vin': ?0, 'field1': { $ne: ?1}}")
    @Update("{$set: {'field1': ?1 }}")
    void updateVinInfoByVinTagF1( String vin, String newField1Value);
```
### Result of `testUpdateOneFieldbyVin`:
```
before update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = 003field1, field2 = 003field2, field3 = 003field3, field4 = }]
===========================
after update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = 002, field2 = 003field2, field3 = 003field3, field4 = }]
===========================

```
## Automatically updating by query

MongoRepository does not support to automatically update queries.
`updateVinInfoByVinMFs` proves that Tag @Update does not allow the use of Condition ($cond) and `$ifNull': [ '003', '$field3' ]`
Verifying the behavior of updateVinInfoByVinMFs is done through the test named testUpdateFieldsbyVin.

### Result:

```
before update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = 003field1, field2 = 003field2, field3 = 003field3, field4 = }]
===========================
after update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = {"$cond": {"if": {"$ne": ["002", "$field1"]}, "then": "002", "else": "$field1"}}, field2 = 004, field3 = 005, field4 = }]
===========================

```

## Manually updating by query

1. **Using MongoRepository:**
To address manual updating, three methods are generated in the `VinInfoRepository` interface, each responsible for updating a specific field (field1, field2, and field3). The methods are named `updateVinInfoByVinTagF1`, `updateVinInfoByVinTagF2`, and `updateVinInfoByVinTagF3` respectively.

The `RepositoryServiceImpl` service utilizes the `updateVinInfo` method to handle the update request.

### Logic of `updateVinInfo`:

- The input object `vinInfo` is used.
- The `vinInfoRepository.findAllByVin` method retrieves all data with a matching VIN from the database.
- Field1 is checked for equality between the input object and the database. If different, `vinInfoRepository.updateVinInfoByVinTagF1` is used to update the field1 value.
- The same process is repeated for field2 and field3.

## Testing the RepositoryServiceImpl:

The purpose of testRepositoryServiceImpl is to validate the functionality of RepositoryServiceImpl.

### Result of `testRepositoryServiceImpl`:

```
before update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = 003field1, field2 = 003field2, field3 = 003field3, field4 = 005field4}]
===========================
Mock input object: 
VinInfo{id = null, field1 = 005field1, field2 = 005field2, field3 = 005field3, field4 = 005field4}
after update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = 005field1, field2 = 005field2, field3 = 005field3, field4 = 005field4}]
===========================

```
### Disadvantage of RepositoryService:

The drawback of `RepositoryService` is the need to manually add methods in `MongoRepository` and rewrite code in `RepositoryServiceImpl`. To overcome this limitation, I recommend using `MongoTemplate`.

2. **MongoTemplate**:

`MongoTemplate` is a class that executes the core MongoDB workflow, eliminating the need for `MongoRepository`. The `MongoTemplateServiceImpl` service employs the `updateVinInfo` method to fulfill the update request.

### Logic of `updateVinInfo`:

- The input object, `vinInfo`, is used to find a data entry in the database where the VIN matches the input object's VIN.
- The `ObjectConvertMap` method converts `vinInfo` and the query result into maps (`mapVin` and `mapQuery` respectively).
- A loop is used to compare the fields between the input object's map and the query map. If the values differ, the values are updated.

### Testing MongoTemplateServiceImpl:

The `testMongoTemplateServiceImpl` is designed to verify the functionality of `MongoTemplateServiceImpl`.


### Result of `testMongoTemplateServiceImpl`

```
before update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = 003field1, field2 = 003field2, field3 = 003field3, field4 = }]
===========================
Mock input object: 
VinInfo{id = null, field1 = 005field1, field2 = 005field2, field3 = 005field3, field4 = 005field4}
after update
[VinInfo{id = 655461f703eeae80a2c1bd06, field1 = 005field1, field2 = 005field2, field3 = 005field3, field4 = 005field4}]
===========================

```