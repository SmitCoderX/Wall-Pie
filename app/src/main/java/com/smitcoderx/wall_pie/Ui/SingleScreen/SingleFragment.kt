package com.smitcoderx.wall_pie.Ui.SingleScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.Ui.UnsplashViewModel
import com.smitcoderx.wall_pie.Util.Constants
import com.smitcoderx.wall_pie.Util.StorageUtility
import com.smitcoderx.wall_pie.databinding.FragmentSingleBinding
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class SingleFragment : Fragment(R.layout.fragment_single), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: FragmentSingleBinding
    private val viewModel by viewModels<UnsplashViewModel>()
    private val args by navArgs<SingleFragmentArgs>()
    private val wallpaperManager by lazy { WallpaperManager.getInstance(requireContext()) }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleBinding.bind(view)

        requestPermissions()

        binding.apply {
            val photos = args.photo

            Glide.with(this@SingleFragment)
                .load(photos.urls?.full)
                .error(R.drawable.ic_placeholder)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbLoading.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbLoading.isVisible = false
                        ivUser.isVisible = true
                        tvUserName.isVisible = true
                        allStuff.isVisible = true
                        ivHeart.isVisible = true
                        singleLikes.isVisible = true
                        return false
                    }
                })
                .into(ivSingle)

            singleLikes.text = "${photos.likes} likes"
            tvUserName.text = photos.user?.username
            Glide.with(this@SingleFragment)
                .load(R.drawable.ic_placeholder)
                .into(ivUser)

            ivHeart.setOnClickListener {
                viewModel.savePhoto(photos)
                Snackbar.make(view, "Wallpaper Saved to Favourites", Snackbar.LENGTH_SHORT).show()
            }

            homeWall.setOnClickListener {
                setWallpaper("HOME")
            }

            lockWall.setOnClickListener {
                setWallpaper("LOCK")
            }

            DownWall.setOnClickListener {
                Snackbar.make(view, "Downloading Started", Snackbar.LENGTH_SHORT).show()
                viewModel.downloadPhotos(requireContext(), photos.urls!!.full)
            }
        }
    }

    private fun requestPermissions() {
        if (StorageUtility.hasStoragePermission(requireContext())) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept storage permissions to use this app",
                Constants.REQUEST_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept storage permissions to use this app",
                Constants.REQUEST_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setWallpaper(type: String) =
        try {
            val bitmap = binding.ivSingle.drawToBitmap()
            wallpaperManager.setBitmap(
                bitmap, null, true, when (type) {
                    "HOME" -> WallpaperManager.FLAG_SYSTEM
                    "LOCK" -> WallpaperManager.FLAG_LOCK
                    else -> WallpaperManager.FLAG_SYSTEM
                }
            )
            Snackbar.make(requireView(), "Wallpaper Applied", Snackbar.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Snackbar.make(requireView(), "Wallpaper Saved to Favourites", Snackbar.LENGTH_SHORT)
                .show()
        }

}