# cloud-resource-file-generator

Used to create ruby files of resources used for Infra-validation-awspec, Infra-validation-azurepec and Infra-validation-gcppec

## How to use this utility ?
### How to build ?
Execute build.sh file to build this utility which will generate fat jar named cloud-resource-0.0.1.jar. You can use this jar to automatically create the ruby file in your local Infra-validation-awspec branch or Infra-validation-azurespec or Infra-validation-gcpspec

### How to run ?
Use following command to run utility jar

java -jar cloud-resource-0.0.1.jar --input-json-file-path=/Users/your_directory/Infra-validation-awspec/references/resource_name.json


After successful execution of above command resource_name_spec,resource_name_attribute and resource_name_.rb files will be generated.
(Give the path of resource_name.json from Infra-validation-awspec/references or Infra-validation-azurespec/references or Infra-validation-gcpspec/references folder only)
