package Megen.data;
import javax.swing.table.AbstractTableModel;
public class TheTableModel extends AbstractTableModel {
    private Megen.data.Group data;
    private static int organization = -1;
    public TheTableModel(Megen.data.Group group) {
        this.data = group;
    }

    @Override
    public int getRowCount() {
        int rowCount = 0;
        for (int i = 0; i < data.getCount(); i++) {
            Organization currentorganization = data.getorganization(i);
            switch (organization) {
                case 1:
                    if (currentorganization instanceof Area) {
                        rowCount++;
                    }
                    break;
                case 2:
                    if (currentorganization instanceof Capital) {
                        rowCount++;
                    }
                    break;
                case 3:
                    if (currentorganization instanceof City) {
                        rowCount++;
                    }
            }
        }
        return rowCount;
    }
    @Override
    public int getColumnCount() {
        return 3;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                data.getorganization(rowIndex).setName((String) aValue);
                break;
            case 1:
                Organization b1 = data.getorganization(rowIndex);
                if (b1 instanceof Area) {
                    ((Area) b1).setBenefit((String) aValue);
                }
                break;
            case 2:
                Organization b2 = data.getorganization(rowIndex);
                if (b2 instanceof Capital) {
                    ((Capital) b2).setBenefit((int) aValue);
                }
                break;
            case 3:
                Organization b3 = data.getorganization(rowIndex);
                if (b3 instanceof City) {
                    ((City) b3).setBenefit((int) aValue);
                }
                break;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Название";
            case 1:
                return "id";
            case 2:
                return "Доп.Инфа";
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data.getorganization(rowIndex).getName();
            case 1: {
                return data.getorganization(rowIndex).getId();
            }
            case 2: {
                switch (organization) {
                    case 1:
                        Organization b1 = data.getorganization(rowIndex);
                        if (b1 instanceof Area )
                            return ((Area) b1).getBenefit();
                        else
                            return null;
                    case 2:
                        Organization b2 = data.getorganization(rowIndex);
                        if (b2 instanceof Capital )
                            return ((Capital) b2).getBenefit();
                        else
                            return null;
                    case 3:
                        Organization b3 = data.getorganization(rowIndex);
                        if (b3 instanceof City)
                            return ((City) b3).getBenefit();
                        else
                            return null;
                }
            }
        }
        return "default";
    }

    public void delete(int ind) {
        this.data.remove(ind);
        fireTableDataChanged(); //Обновление таблицы после удаления
    }

    public void reload_table() {
        fireTableDataChanged();
    }
    public static void setorganization(int organizationIN) {
        organization = organizationIN;
    }
}
