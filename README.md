# Evaluation Script of VIST Challenge at NAACL 2018

This Github repository contains the official evaluation script of the [VIST Challenge at NAACL 2018](http://visionandlanguage.net/workshop2018/#challenge).

## Evaluation Procedure

1. In [VIST Challenge at NAACL 2018](http://visionandlanguage.net/workshop2018/#challenge), the competition team will submit (only) one story *Story<sub>test</sub>* for each photo sequence. 
2. For each photo sequence, *N* human-generated stories *Story<sub>human-1</sub>*, *Story<sub>human-2</sub>*, ..., *Story<sub>human-N</sub>* have been collected as the gold-standard stories.

For each submitted story *Story<sub>test</sub>*, this evaluation script: 

1. first calculates its **maximum** [Meteor](http://www.cs.cmu.edu/~alavie/METEOR/README.html) score against each of all gold-standard stories of the same photo sequence (*Story<sub>human-1</sub>* to *Story<sub>human-N</sub>*); and then
2. calculates the **average** maximum [Meteor](http://www.cs.cmu.edu/~alavie/METEOR/README.html) score of all photo sequences. 


## Runnable JAR File

You can download the entire `runnable_jar` folder and run `EvalVIST.jar` as it is.

```
runnable_jar/EvalVIST.jar
```

## JAR File Usage

```
java -jar EvalVIST.jar -testFile <test_file_path> -gsFile <gs_file_path>
```

You can also use JVM parameter `-Xmx` to set maximum Java heap size, e.g., `java -Xmx2g -jar EvalVIST.jar -testFile <test_file_path> -gsFile <gs_file_path>`.


For running `EvalVIST.jar`, as shown in `runnable_jar` folder, please put:

1. `data` folder (including the `paraphrase-en.gz` in it. Can be found in `src/main/resources/meteor-1.5` folder)
2. `vist-challenge-template.json` (can be found in `src/main/resources` folder)

at the same folder of `EvalVIST.jar` file.

The template file (`vist-challenge-template.json`) is provided by the hosts of VIST Challenge for specifying which photo sequences are included in this challenge.
A few (~3%) photo sequences in the VIST test set are *not* included in the challenge because these photos have been removed from Flickr by their owners. 

The following two parameters are both **required**:

| Parameter | Description |
| ------------ | ------------- |
| **testFile** | The path of **your submission file**, which contains exactly one story for each photo sequence. Please see the following section for format details. |
| **gsFile** | The path of **gold-standard file**, which contains the stories that were written by human workers. Please go to the [VIST website](http://visionandlanguage.net/VIST/dataset.html) to download the test set (~17MB, `test.story-in-sequence.json`) of Images-in-Sequence (SIS) data. For the VIST challenge, we also collected 3 extra new stories for each photo sequence in the test set. This extra test set is not public. |

## Submission Format

Please upload your submissions as a JSON file in the following format:

```json
{
  "team_name": "example_team_name",
  "evaluation_info": {
    "additional_description": "comments or notes about this submission."
  },
  "output_stories": [
    {
      "album_id": "flickr_album_id",
      "photo_sequence": [
          "flickr_photo_id_1",
          "flickr_photo_id_2",
          "flickr_photo_id_3",
          "flickr_photo_id_4",
          "flickr_photo_id_5"
        ],
      "story_text_normalized": "normalized text of your story"
    },
    {
     "album_id": "flickr_album_id",
      "photo_sequence": [
          "flickr_photo_id_1",
          "flickr_photo_id_2",
          "flickr_photo_id_3",
          "flickr_photo_id_4",
          "flickr_photo_id_5"
        ],
      "story_text_normalized": "normalized text of your story"
    },
    {
      "album_id": "flickr_album_id",
      "photo_sequence": [
          "flickr_photo_id_1",
          "flickr_photo_id_2",
          "flickr_photo_id_3",
          "flickr_photo_id_4",
          "flickr_photo_id_5"
        ],
      "story_text_normalized": "normalized text of your story"
    }...
  ]
}
```

Your submitted JSON file also needs to satisfy the following requirements, or it will be rejected by the system:

1. For each story, please concatenate all the sentences together, with a space in between, to form a the story.
2. Your JSON file should have only one single story for each photo sequence.
3. Your JSON file should contain stories for all the photo sequences listed in the template file (`vist-challenge-template.json`). You can simply take the template file and fill in the `story_text_normalized` field of each photo sequence.
4. Any non-ASCII characters will be ignored for evaluation.

## Example Submission Files

We also provide the following example submission files in the `example_submission_json` folder.
You can use these files as the test file to try out the evaluation script.


|File Name|Description|
|---------|-----------|
|**example-submission-file.test.json**|Contains the first human-generated story of each photo sequence in the VIST test set. If using the VIST test set as gsFile, the resulting METEOR score output should be 1 (or 0.99999999...).|
|**example-submission-file-extra.empty.test.json**|All stories are empty strings. The resulting METEOR score output should be 0.|
|**example-submission-file-extra.happy.test.json**|Simple baseline. All stories are "everyone is happy ." repeating 5 times.|
|**example-submission-file-extra.missing.happy.test.json**|This file missing one story. The script should report errors.|

## Example Output

```
[Test File] ..\example_submission_json\example-submission-file.test.json
[Passed] Test file is in valid JSON syntax.
[Passed] Each photo sequence has only one story.
[Passed] All required stories are submitted.
MeteorConfiguration...
setTask...
scorer created...
--------------------------
Avg. Max Meteor Score =
0.9999520462921719
```








## Contact

This repository is created and maintained by Ting-Hao (Kenneth) Huang (tinghaoh@cs.cmu.edu).

