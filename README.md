# OCR

Application made to extract messages from a video.

Contains three main steps:

* Apply split in video

This step breaks the video into frames every second. Using javacv library.

* Apply filter in frames

This step applies multiple filters and crops the image to make it easier to read the sentences. Using aspose.

* Apply OCR in frames

This step applies OCR to identify the sentences. Using tesseract library.

* Apply spell checker in sentences

This step applies spell checker in identified sentences. Using edit distance algorithm.

* Apply parallelism

This step uses the concurrent paradigm to be able to process for more than one video at the same time.
