package db;

public class Cliente{

    private int id_usuario;
    private String nombre;
    private String email;
    private int telefono;
    private String fecha_registro;
    private String nombre_usuario_FK;

    public Cliente(int id_usuario, String nombre, String email, int telefono, String fecha_registro, String nombre_usuario_FK) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha_registro = fecha_registro;
        this.nombre_usuario_FK = nombre_usuario_FK;
    }

	public int getId_usuario() {
		return this.id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getFecha_registro() {
		return this.fecha_registro;
	}

	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public String getNombre_usuario_FK() {
		return this.nombre_usuario_FK;
	}

	public void setNombre_usuario_FK(String nombre_usuario_FK) {
		this.nombre_usuario_FK = nombre_usuario_FK;
	}

}