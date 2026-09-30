package cl.dsy1102.fonda;

public abstract class Bebida {

    protected String nombre;
    protected int volumenML;
    protected int stock;

    public Bebida(String nombre,int volumenML,int stock){
        setNombre(nombre);
        setVolumenML(volumenML);
        setStock(stock);
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        if (nombre==null || nombre.equals("")){
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo ni estar vacio");
        }
        this.nombre=nombre;
    }

    public int getVolumenML(){
        return volumenML;
    }

    public void setVolumenML(int volumenML){
        if (volumenML<100 || volumenML >3000) {
            throw new IllegalArgumentException("El volumen debe estar entre 100 y 3000 mililitros");
        }
        this.volumenML=volumenML;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        if (stock<0) {
            throw new IllegalArgumentException("La cantidad de stock debe ser mayor que 0.");

        }
        this.stock=stock;
    }

    public abstract double calcularPrecio();

    public abstract String obtenerDetalle();


    @Override
    public String toString(){
        return "Nombre: " + nombre  + " | Volumen: " + volumenML + " ml" ;
    }






























}
