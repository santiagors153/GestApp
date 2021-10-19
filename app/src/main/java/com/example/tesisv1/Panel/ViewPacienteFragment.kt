package com.example.tesisv1.Panel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.tesisv1.Database.PacienteApplication
import com.example.tesisv1.Database.PacienteEntity
import com.example.tesisv1.MenuHistorial
import com.example.tesisv1.R
import com.example.tesisv1.databinding.FragmentViewPacienteBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ViewPacienteFragment : Fragment() {

    private lateinit var mBinding: FragmentViewPacienteBinding
    private var mActivity: MenuHistorial? = null
    private var mIsViewMode :Boolean = false
    private var mPacienteEntity: PacienteEntity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentViewPacienteBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong(getString(R.string.args_id), 0)
        if (id != null && id != 0L){
            mIsViewMode = true
            getPaciente(id)
        }else{
            mIsViewMode = false
        }

        mActivity = activity as? MenuHistorial
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.edith_paciente_message_view_success)
        setHasOptionsMenu(true)

    }

    private fun getPaciente(id: Long) {
        doAsync {
            mPacienteEntity = PacienteApplication.database.pacienteDao().getPacinteById(id)
            uiThread {if(mPacienteEntity != null) setUIPaciente(mPacienteEntity!!)}
        }
    }

    private fun setUIPaciente(pacienteEntity: PacienteEntity) {
        with(mBinding){
            tvNombre.text = pacienteEntity.nombre.trim()
            tvApellidoP.text = pacienteEntity.ApellidoP.trim()
            tvApellidoM.text = pacienteEntity.ApellidoM.trim()
            tvDNI.text = pacienteEntity.DNI.trim()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mActivity?.hideFab(true)
        super.onDestroy()
    }

}