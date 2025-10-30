package logic;

public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 1230;

    // 🔐 Usuario y Sesión (900–909)
    public static final int USUARIO_LOGIN = 900;
    public static final int USUARIO_LOGOUT = 901;
    public static final int USUARIO_READ = 902;
    public static final int USUARIO_CREATE = 903;
    public static final int USUARIO_UPDATE = 904;
    public static final int USUARIO_DELETE = 905;

    // 👨‍⚕️ Medico (910–919)
    public static final int MEDICO_CREATE = 910;
    public static final int MEDICO_READ = 911;
    public static final int MEDICO_UPDATE = 912;
    public static final int MEDICO_DELETE = 913;
    public static final int MEDICO_SEARCH = 914;

    // 👩‍🔬 Farmaceuta (920–929)
    public static final int FARMACEUTA_CREATE = 920;
    public static final int FARMACEUTA_READ = 921;
    public static final int FARMACEUTA_UPDATE = 922;
    public static final int FARMACEUTA_DELETE = 923;
    public static final int FARMACEUTA_SEARCH = 924;

    // 🧑‍💼 Admin (930–939)
    public static final int ADMIN_CREATE = 930;
    public static final int ADMIN_READ = 931;
    public static final int ADMIN_UPDATE = 932;
    public static final int ADMIN_DELETE = 933;
    public static final int ADMIN_SEARCH = 934;

    // 🧍 Paciente (940–949)
    public static final int PACIENTE_CREATE = 940;
    public static final int PACIENTE_READ = 941;
    public static final int PACIENTE_UPDATE = 942;
    public static final int PACIENTE_DELETE = 943;
    public static final int PACIENTE_SEARCH = 944;

    // 💊 Medicamento (950–959)
    public static final int MEDICAMENTO_CREATE = 950;
    public static final int MEDICAMENTO_READ = 951;
    public static final int MEDICAMENTO_UPDATE = 952;
    public static final int MEDICAMENTO_DELETE = 953;
    public static final int MEDICAMENTO_SEARCH = 954;

    // 📦 ItemReceta (960–969)
    public static final int ITEMRECETA_CREATE = 960;
    public static final int ITEMRECETA_READ = 961;
    public static final int ITEMRECETA_UPDATE = 962;
    public static final int ITEMRECETA_DELETE = 963;
    public static final int ITEMRECETA_SEARCH = 964;
    public static final int RECETA_READ_ALL = 975;           // NUEVO: listar todas
    public static final int RECETA_SEARCH_BETWEEN = 976;     // NUEVO: buscar por rango de fechas

    // 📄 Receta (970–979)
    public static final int RECETA_CREATE = 970;
    public static final int RECETA_READ = 971;
    public static final int RECETA_UPDATE = 972;
    public static final int RECETA_DELETE = 973;
    public static final int RECETA_SEARCH = 974;

    // 📝 Prescripcion (980–989)
    public static final int PRESCRIPCION_CREATE = 980;
    public static final int PRESCRIPCION_READ = 981;
    public static final int PRESCRIPCION_UPDATE = 982;
    public static final int PRESCRIPCION_DELETE = 983;
    public static final int PRESCRIPCION_SEARCH = 984;

    // ⚠️ Errores
    public static final int ERROR_NO_ERROR = 0;
    public static final int ERROR_ERROR = 1;

    // 🔌 Control de conexión
    public static final int DISCONNECT = 999;
    // 👨‍⚕️ Medico
    public static final int MEDICO_READ_ALL = 915;

    // 👩‍🔬 Farmaceuta
    public static final int FARMACEUTA_READ_ALL = 925;

    // 🧑‍💼 Admin
    public static final int ADMIN_READ_ALL = 935;

    // 🧍 Paciente
    public static final int PACIENTE_READ_ALL = 945;

    // 💊 Medicamento
    public static final int MEDICAMENTO_READ_ALL = 955;

}
