// Frontend/src/main/java/presentation/Medicamento/presentation/MedicamentoController.java
package hospital.presentation.Medicamento.presentation;

import hospital.Application;
import hospital.logic.Service;
import hospital.presentation.Refresher;
import hospital.presentation.ThreadListener;
import logic.Medicamento;

import javax.swing.*;
import java.util.List;

public class MedicamentoController implements ThreadListener {
    private final MedicamentoView view;
    private final MedicamentoModel model;
    private Refresher refresher;

    public MedicamentoController(MedicamentoView view, MedicamentoModel model) throws Exception {
        this.view = view;
        this.model = model;

        model.init();
        model.setList(Service.instance().findAllMedicamentos());

        view.setController(this);
        view.setModel(model);

        refresher = new Refresher(this);
        refresher.start();
    }

    public void search(Medicamento filter) {
        try {
            model.setFilter(filter);
            List<Medicamento> rows = Service.instance().searchMedicamento(filter);
            model.setMode(Application.MODE_CREATE);
            model.setCurrent(new Medicamento());
            model.setList(rows);
        } catch (Exception e) {}
    }

    public void save(Medicamento m) {
        try {
            switch (model.getMode()) {
                case Application.MODE_CREATE -> Service.instance().createMedicamento(m);
                case Application.MODE_EDIT -> Service.instance().updateMedicamento(m);
            }
            model.setFilter(new Medicamento());
            search(model.getFilter());
        } catch (Exception ex) {}
    }

    public void edit(int row) {
        Medicamento m = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().readMedicamento(m.getCodigo()));
        } catch (Exception ex) {}
    }

    public void delete() {
        try {
            Service.instance().deleteMedicamento(model.getCurrent().getCodigo());
            search(model.getFilter());
        } catch (Exception ex) {}
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Medicamento());
        view.getNombreBuscar().setText("");
        view.getDescripcionBuscar().setText("");
        view.getCodigoBuscar().setText("");
    }

    @Override
    public void refresh() {
        try {
            model.setList(Service.instance().findAllMedicamentos());
        } catch (Exception e) {}
    }

    public void reporte() {
        StringBuilder sb = new StringBuilder("Lista de Medicamentos:\n\n");
        for (Medicamento m : model.getList()) {
            sb.append("Código: ").append(m.getCodigo())
                    .append(" | Nombre: ").append(m.getNombre())
                    .append(" | Desc: ").append(m.getDescripcion())
                    .append("\n-------------------------\n");
        }
        JOptionPane.showMessageDialog(view.getMainPanel(), sb.toString(), "Reporte", JOptionPane.INFORMATION_MESSAGE);
    }

    public void stop() {
        if (refresher != null) refresher.stop();
    }
}