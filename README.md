# Evaluation Script of VIST Challenge at NAACL 2018

This Github repo contains the official evaluation script of the VIST Challenge at NAACL 2018.
This repo is created and maintained by Ting-Hao (Kenneth) Huang (tinghaoh@cs.cmu.edu).

## Runnable JAR File Download (.zip)


## JAR File Usage

```
java -jar EvalVIST.jar -testFile <test_file_path> -gsFile <gs_file_path>
```


| Parameter (All required) | Description |
| ------------ | ------------- |
| testFile | Your submission file, which contains exactly one story for each photo sequence. Please see the following section for format details. |
| gsFile | Gold-standard file, which means the stories were written by human workers. Please go to the VIST website (http://visionandlanguage.net/VIST/dataset.html) to download the test set (~17MB, "test.story-in-sequence.json") of Images-in-Sequence (SIS) data. For the VIST challenge, we also collected 3 extra new stories for each photo sequence in the test set. This extra test set is not public. |



## Submission Format

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
