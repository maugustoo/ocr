# OCR

Application made to extract messages from a video.

Contains three main steps:

* Apply split in video

This step breaks the video into frames every second. Using javacv library.

* Apply filter in frames

This step applies multiple filters and crops the image to make it easier to read the sentences. Using aspose.

* Apply OCR in frames

This step applies OCR to identify the sentences. Using tesseract library.
