# Evaluation Script of VIST Challenge at NAACL 2018

This Github repository contains the official evaluation script of the [VIST Challenge at NAACL 2018](http://visionandlanguage.net/workshop2018/#challenge).

## Runnable JAR File

You can download the entire `runnable_jar` folder and run `EvalVIST.jar` as it is.

```
runnable_jar/EvalVIST.jar
```

## JAR File Usage

```
java -jar EvalVIST.jar -testFile <test_file_path> -gsFile <gs_file_path>
```

For running `EvalVIST.jar`, as shown in `runnable_jar` folder, please put:

1. `data` folder (including the `paraphrase-en.gz` in it), and
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


## Contact

This repository is created and maintained by Ting-Hao (Kenneth) Huang (tinghaoh@cs.cmu.edu).

