# BIOSSES

BioSSES computes similarity of biomedical sentences by utilizing WordNet as the general domain ontology and UMLS as the biomedical domain specific ontology.

We allow you to compute sentence similarity with the following methods:

- Qgram [0-1]
- Wordnet [0-1]
- UMLS [0-1]
- Paragraph Vector [0-1]
- Combined Ontology (Wordnet and UMLS) [0-1]
- Supervised Approach [0-4]

Requirements for running BIOSSES locally on your computer:
	Java 7 (JRE 1.7) or higher
	Perl (For Windows: "C:\Strawberry\perl\bin\perl.exe")

DOWNLOAD RESOURCES:
Before starting to use the system you have to download necessary resources:
  - On Linux run './get_resources.sh', it will download and extract the files.
  - On Windows get the link from the file and manually download and extract 'resources.zip' 
    to 'src\main\resources\' subdirectory.  

To use from command-line:
  - Open repo: cd biosses
  - Clean: mvn clean
  - Compile: mvn install
  - Analyse corpus (Linux): ./run.sh sample.txt
  - The corpus should contain a pair of sentences per line separated by |||

	
The following are usage examples of different methods provided by BIOSSES for measuring semantic similarity between sentences.

 ### Ontology-based Semantic Similarity Metrics

 `CombinedOntologyMethod semanticSimilarity = new CombinedOntologyMethod();`
 
 `similarityScore = semanticSimilarity.getSimilarityForWordnet(sentence1, sentence2);`
 
 `similarityScore = semanticSimilarity.getSimilarityForUMLS(sentence1, sentence2);`
 
 `similarityScore = semanticSimilarity.getSimilarity(sentence1, sentence2);`
 
 ### String Similarity Metrics
 
 `StringMetric metric = StringMetrics.qGramsDistance();`
 
 `similarityScore = metric.compare(sentence1, sentence2);`

 ### Paragraph Vector Method 
 
 `SentenceVectorsBasedSimilarity sentenceVectorsBasedSimilarity = new SentenceVectorsBasedSimilarity();`
 
 `similarityScore = sentenceVectorsBasedSimilarity.getSimilarity(preprocessedS1, preprocessedS2);`

 ### Supervised Semantic Similarity Method
 
 `LinearRegressionMethod linearRegressionMethod = new LinearRegressionMethod();`
 
 `similarityScore = linearRegressionMethod.getSimilarity(sentence1, sentence2);`


sentence1 and sentence2 are the <String> parameters to be compared. 
CombinedOntologyMethod has one more constructor which takes a List<String> parameter. You can call this if you want to use your own stop words list in this type.
                                        						 							  			  
If you use this system, please cite the following paper:
Soğancıoğlu, Gizem, Hakime Öztürk, and Arzucan Özgür. "BIOSSES: a semantic sentence similarity estimation system for the biomedical domain." Bioinformatics 33.14 (2017): i49-i58.

For more information and any problems please contact: gizemsogancioglu@gmail.com



	
