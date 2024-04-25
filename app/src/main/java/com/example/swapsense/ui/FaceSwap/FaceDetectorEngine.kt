package com.example.swapsense.ui.FaceSwap

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

/**
 * A class to encapsulate the ML Kit Face Detection functionality.
 * Provides methods to initialize face detection with high accuracy options,
 * to process images, and to release resources when no longer needed.
 */
class FaceDetectorEngine() {

    // TODO
    // Declare FaceDetector

    // TODO
    // Implement FaceDetectorOptions


    // TODO
    init {
        // Detect Face

    }

    // TODO
    // close the detector

    fun stop() {
    }

    // TODO
    fun detectInImage(image: InputImage): Task<List<Face>> {
        // Return detected image
    }

}