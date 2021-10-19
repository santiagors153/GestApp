package com.example.tesisv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tesisv1.Database.PacienteApplication
import com.example.tesisv1.Database.PacienteEntity
import com.example.tesisv1.Panel.EdithPacienteFragment
import com.example.tesisv1.Panel.ViewPacienteFragment
import com.example.tesisv1.databinding.ActivityMenuHistorialBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MenuHistorial : AppCompatActivity(), OnClickListener, MainAux {
    private lateinit var mBinding: ActivityMenuHistorialBinding
    private lateinit var mAdapter: PacienteAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMenuHistorialBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.fab.setOnClickListener {
            launchEdithFragment()
        }
        setupRecyclerView()
    }

    //Esta funcion se encargar de traer a los fragment
    private fun launchEdithFragment(args: Bundle? = null) {
        val fragment = EdithPacienteFragment()
        if (args != null)fragment.arguments = args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        hideFab()

    }


    private fun setupRecyclerView() {
        mAdapter = PacienteAdapter(mutableListOf(),this)
        mGridLayout = GridLayoutManager(this,resources.getInteger(R.integer.main_columns))
        getSPacientes()
        mBinding.ReciclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter

        }
    }

    private fun getSPacientes(){
        doAsync {
            val paciente = PacienteApplication.database.pacienteDao().getAllPaciente()
            uiThread {
                mAdapter.setPaciente(paciente)
            }
        }
    }

    override fun onClick(pacienteId: Long) {
        val args = Bundle()
        args.putLong(getString(R.string.args_id), pacienteId)
        launchViewFragment(args)
    }

    private fun launchViewFragment(args: Bundle? = null) {
        val fragment = ViewPacienteFragment()
        if (args != null)fragment.arguments = args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        hideFab()
    }



    override fun onFavoriteStore(pacienteEntity: PacienteEntity) {
        pacienteEntity.estado = !pacienteEntity.estado
        doAsync {
            PacienteApplication.database.pacienteDao().updatePaciente(pacienteEntity)
            uiThread {
                updatePaciente(pacienteEntity)
            }
        }
    }

    override fun onDeleteStore(pacienteEntity: PacienteEntity) {
        pacienteEntity.estado = !pacienteEntity.estado
        doAsync {
            PacienteApplication.database.pacienteDao().deletePaciente(pacienteEntity)
            uiThread {
                mAdapter.delete(pacienteEntity)
            }
        }
    }


    //Funcion que sirve para esconder el boton de agregar paciente
    override fun hideFab(isVisible: Boolean) {
        if (isVisible) mBinding.fab.show() else mBinding.fab.hide()
    }

    override fun updatePaciente(pacienteEntity: PacienteEntity) {
        mAdapter.update(pacienteEntity)
    }

    override fun addPaciente(pacienteEntity: PacienteEntity) {
        mAdapter.add(pacienteEntity)
    }

}

