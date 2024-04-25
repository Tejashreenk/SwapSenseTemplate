package com.example.swapsense.ui.FaceSwap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.swapsense.databinding.FragmentNotificationsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Constants and state variables.
    private val tag = "MainActivity"
    private val face1Tab = 0
    private val face2Tab = 1
    private val pickImage = 100
    private var selectedTab = 0

    private val desiredWidth = 800
    private val desiredHeight = 800

    // Variables to hold image URIs for faces.
    private var imageUriFace1: Uri? = null
    private var imageUriFace2: Uri? = null

    // Bitmaps for original and swapped faces.
    private lateinit var bitmap1: Bitmap
    private lateinit var bitmap2: Bitmap
    private lateinit var bitmap1Swapped: Bitmap
    private lateinit var bitmap2Swapped: Bitmap

    // UI components.
    private lateinit var imageView: ImageView
    private lateinit var swapButton: FloatingActionButton

    private lateinit var faces1: List<Face>
    private lateinit var faces2: List<Face>
    // Engine for face detection.
    private val faceDetectorEngine = FaceDetectorEngine()

    // Flags to track the completion of face detection and swapping.
    private var face1Done = false
    private var face2Done = false
    private var okToSwap = false
    private var hasSwapped = false

    private lateinit var addImageButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabs = binding.tabLayout
        // TODO
        // Initialize all layout views
        // Enable swap button only when both images selected
        // Set to false by default

        // Change tabs
        // Setup listener for tab selection to switch images and visibility of add button.
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab != null) {
                    //HINT
                    Log.d(tag, "Tab ${tab.position} selected")


                    if (hasSwapped) {
                        // TODO
                        // Swapped, used swapped bitmaps instead of source.

                    } else {
                        // TODO
                        // Has not swapped, use sources.
                        // Hide Button if Image selected
                        // Show image if image selected on the correct faceTab selected



                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d(tag, "onTabReselected not in use.")
                if (tab != null) {
                    if (tab.position == face1Tab) {
                        if (imageUriFace1 != null) {
                            addImageButton.visibility = Button.GONE
                        } else {
                            addImageButton.visibility = Button.VISIBLE
                        }
                    }

                    if (tab.position == face2Tab) {
                        if (imageUriFace2 != null) {
                            addImageButton.visibility = Button.GONE
                        } else {
                            addImageButton.visibility = Button.VISIBLE
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d(tag, "onTabUnselected not in use.")
            }
        })

        // TODO
        // Open gallery for image selection on click of the button
        // Request for permission to access Gallery


        // TODO
        // Click listener for Swap  action button, should result in face swap.
        // Check if it's okay to proceed with the swap
        // Arrange landmarks (See Landmarks File) for the first set of faces
        // Arrange landmarks (See Landmarks File) for the second set of faces
        // Perform face swap from first to second image (See Swap)
        // Perform face swap from second to first image (See Swap)
        // Update the image view to show the swapped face depending on the selected tab
        // Set flag indicating that the swap has occurred

    }

    // TODO
    // Gallery
    // Function to open Gallery and pick image


    // TODO
    // Callback for the result from requesting permissions
    // Function to handle image selection and permission requests.


    // TODO
    // Handle the result from gallery selection.
    // Display the image in imageview
    // Hide addImageButton when image displayed



    // Prepares chosen image for face detection
    private fun prepareImage(uri: Uri, faceIndex: Int) {
        Log.d(tag, "prepareImage: Preparing image for face detection.")

        Glide.with(this)
            .asBitmap()
            .load(uri)
            .into(object : CustomTarget<Bitmap>(desiredWidth, desiredHeight) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    val inputImage = InputImage.fromBitmap(resource, 0)
                    hasSwapped = false

                    when (faceIndex) {
                        0 -> bitmap1 = resource
                        else -> bitmap2 = resource
                    }
// HW
                    // TODO
                    // Use faceDetectorEngine to detect faces
                    faceDetectorEngine.detectInImage(inputImage)
                        .addOnSuccessListener { faces ->
                            // TODO
                            // Set faces based on faceIndex


                            val notEmpty = faces.isNotEmpty()
                            if (notEmpty && faceIndex == 0) {
                                face1Done = true
                            }
                            if (notEmpty && faceIndex == 1) {
                                face2Done = true
                            }

                            // TODO
                            // Set okToSwap flag
                            // Enable swapButton based on okToSwap flag

                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })


    }

    //    Draws landmarks for a face. Only for debugging.
    private fun drawLandmarks(uri: Uri, landmarksForFaces: ArrayList<ArrayList<PointF>>) {
        Log.v(tag, "Draw landmarks for faces.")

        Glide.with(this)
            .asBitmap()
            .load(uri)
            .into(object : CustomTarget<Bitmap>(desiredWidth, desiredHeight) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val bitmapWithLandmarks =
                        ImageUtils.drawLandmarksOnBitmap(resource, landmarksForFaces)

                    imageView.setImageBitmap(bitmapWithLandmarks)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    // TODO
    // Declare Request codes for camera and permissions


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}