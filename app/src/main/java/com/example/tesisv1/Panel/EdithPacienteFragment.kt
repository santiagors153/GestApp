package com.example.tesisv1.Panel

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.tesisv1.Database.PacienteApplication
import com.example.tesisv1.Database.PacienteEntity
import com.example.tesisv1.MenuHistorial
import com.example.tesisv1.R
import com.example.tesisv1.databinding.FragmentEdithPacienteBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EdithPacienteFragment : Fragment() {

    private lateinit var mBinding: FragmentEdithPacienteBinding
    private var mActivity: MenuHistorial? = null
    private var mIsEdithMode :Boolean = false
    private var mPacienteEntity:PacienteEntity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding = FragmentEdithPacienteBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong(getString(R.string.args_id), 0)
            mIsEdithMode = false
            mPacienteEntity = PacienteEntity(nombre = "",ApellidoP = "",ApellidoM = "" , telefono = "",HitoriaCLin = 0, DNI = "", Edad = 0)
        setupActionBar()
        setupTextField()
    }

    private fun setupActionBar() {
        mActivity = activity as? MenuHistorial
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.edith_paciente_message_success)
        setHasOptionsMenu(true)
    }

    private fun setupTextField() {
        with(mBinding) {

            etName.addTextChangedListener { validateFields(tilName) }
            etApellidoP.addTextChangedListener { validateFields(tilApellidoP) }
            etApellidoM.addTextChangedListener { validateFields(tilApellidoM) }
            etPhone.addTextChangedListener { validateFields(tilPhone) }
            etHistoria.addTextChangedListener { validateFields(tilHistoria) }
            etDNI.addTextChangedListener { validateFields(tilDNI) }
            etEdad.addTextChangedListener { validateFields(tilEdad) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home  -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {
                if (mPacienteEntity != null && validateFields(mBinding.tilName, mBinding.tilHistoria, mBinding.tilDNI, mBinding.tilEdad)){
                    /*val store = StoreEntity()*/
                    with(mPacienteEntity!!){
                        nombre = mBinding.etName.text.toString().trim()
                        ApellidoP = mBinding.etApellidoP.text.toString().trim()
                        ApellidoM = mBinding.etApellidoM.text.toString().trim()
                        Edad = mBinding.etEdad.text.toString().toInt()
                        HitoriaCLin = mBinding.etHistoria.text.toString().toInt()
                        telefono = mBinding.etPhone.text.toString().trim()
                        DNI = mBinding.etDNI.text.toString().trim()
                    }
                    doAsync {
                        if (mIsEdithMode) PacienteApplication.database.pacienteDao().updatePaciente(mPacienteEntity!!)
                        else
                            mPacienteEntity!!.id = PacienteApplication.database.pacienteDao().addPaciente(mPacienteEntity!!)
                        uiThread {
                            if (mIsEdithMode){
                                mActivity?.updatePaciente(mPacienteEntity!!)
                                Snackbar.make(mBinding.root,
                                    R.string.edith_store_message_update_success
                                    ,Snackbar.LENGTH_SHORT)
                                    .show()
                            }else {
                                mActivity?.addPaciente(mPacienteEntity!!)
                                hideKeyboard()
                                Toast.makeText(
                                    mActivity,
                                    R.string.edith_store_message_save_success,
                                    Toast.LENGTH_SHORT
                                ).show()
                                mActivity?.onBackPressed()
                            }
                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //Funcion que sirve para ocultar el teclado de despues de insertar los datos
    private fun hideKeyboard(){
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null){
            imm.hideSoftInputFromWindow(view?.windowToken,0)
        }
    }
    //Funcion que sirve para eliminar el teclado des pues de regresar del fragment
    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }
    //Funcion que sirve para validar que los campos seleccionados dentro del fragment no se queden vacios
    private fun validateFields(vararg textFields: TextInputLayout): Boolean{
        var isValid = true
        for (textField in textFields){
            if (textField.editText?.text.toString().trim().isEmpty()){
                textField.error = getString(R.string.helpe_require)
                textField.editText?.requestFocus()
                isValid = false
            }else textField.error = null
        }

        if (!isValid) Snackbar.make(mBinding.root,
            R.string.edit_store_message_valid,
            Snackbar.LENGTH_SHORT).show()

        return isValid
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mActivity?.hideFab(true)
        super.onDestroy()
    }




}