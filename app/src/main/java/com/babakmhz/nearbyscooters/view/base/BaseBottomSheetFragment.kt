package com.babakmhz.nearbyscooters.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseBottomSheetFragment : BottomSheetDialogFragment(), BaseViewHelper {


}