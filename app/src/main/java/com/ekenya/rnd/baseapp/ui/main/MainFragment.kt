package com.ekenya.rnd.baseapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.ekenya.rnd.baseapp.BaseApp
import com.ekenya.rnd.baseapp.databinding.FragmentMainBinding
import com.ekenya.rnd.baseapp.di.helpers.activities.ActivityHelperKt
import com.ekenya.rnd.baseapp.di.helpers.activities.AddressableActivity
import com.ekenya.rnd.baseapp.di.helpers.features.FeatureModule
import com.ekenya.rnd.baseapp.di.helpers.features.Modules
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import javax.inject.Inject

class MainFragment : BaseDaggerFragment() {

    private lateinit var binding: FragmentMainBinding
    companion object {
        fun newInstance() = MainFragment()
    }

    private var mApp: BaseApp? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val module by lazy {
        Modules.FeatureOfflineBanking.INSTANCE
    }

    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(requireActivity())
    }

    private val listener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                setStatus("DOWNLOADING")
            }
            SplitInstallSessionStatus.INSTALLING -> {
                setStatus("INSTALLING")
            }
            SplitInstallSessionStatus.INSTALLED -> {

                // Enable module immediately
                activity?.let { SplitCompat.install(it) }

                setStatus("${module.name} already installed\nPress start to continue ..")
                //
                binding.startButton.visibility = View.VISIBLE
                binding.startButton.setOnClickListener{
                    showFeatureModule(module)
                }
            }
            SplitInstallSessionStatus.FAILED -> {
                setStatus("FAILED")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        mApp = activity?.application as BaseApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        //
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val data = mViewModel.getData()

        Log.i("MainFragment", "=> $data")

        if (splitInstallManager.installedModules.contains(module.toString())) {
            showFeatureModule(module)
//            setStatus("${module.name} already installed\nPress start to continue ..")
//            //
//            binding.startButton.visibility = View.VISIBLE
//            binding.startButton.setOnClickListener{
//                showFeatureModule(module)
//            }
            return
        }

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(module.name)
            .build()

        splitInstallManager.startInstall(request)
        setStatus("Start install for ${module.name}")
    }
    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener(listener)
    }

    override fun onPause() {
        splitInstallManager.unregisterListener(listener)
        super.onPause()
    }

    private fun setStatus(label: String){
        //binding.status.text = label
        Toast.makeText(context,label,Toast.LENGTH_SHORT).show()
    }
    /**
     *
     */
    private fun showFeatureModule(module: FeatureModule)
    {
        try {
            //Inject
            mApp!!.addModuleInjector(module)
            //

            this.startActivity(ActivityHelperKt.intentTo(requireActivity(), module as AddressableActivity))
            //finish();
        } catch (e: Exception) {
            e.message?.let { Log.d("MainFragment", it) };
        }
    }
}