package db;

public class Cliente{

    private int id_cliente;
    private String nombre;
	private String email;
	private int telefono;
	public String password;
    private String fecha_registro;
    private String nombre_usuario_FK;

    public Cliente(int id_cliente, String nombre, String email, int telefono,String password, String fecha_registro, String nombre_usuario_FK) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.email = email;
		this.telefono = telefono;
		this.password = password;
        this.fecha_registro = fecha_registro;
        this.nombre_usuario_FK = nombre_usuario_FK;
    }

	public int getId_cliente() {
		return this.id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
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

	public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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