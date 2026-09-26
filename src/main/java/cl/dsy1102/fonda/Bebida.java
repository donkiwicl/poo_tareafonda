package cl.dsy1102.fonda;

public abstract class Bebida {
    private String nombre;
    private int volumenMl;
    private int stock;

    public Bebida(String nombre, int volumenMl, int stock) {
        setNombre(nombre);
        setVolumenMl(volumenMl);
        setStock(stock);

        //Falta aca poner todas las validaciones
        // nombre	No puede ser nulo ni vacío.
        // volumenDebe encontrarse en el rango entre 100 y 3.000 mililitros.
        //stock	Debe ser un valor mayor que cero.


    }


    public abstract Double calcularPrecio ();
    public String obtenerDetalle(){
        return nombre +"\n"+volumenMl+"ml\n"+"Stock: " + stock;
    }

    @Override
    public String toString() {
        return nombre + " " + volumenMl + "ml";
    }

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("No puede ser nulo ni vacio");
        }
    }

    public int getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(int volumenMl) {
        if (100 <= volumenMl && volumenMl <= 3000){
            this.volumenMl = volumenMl;
        } else{
            throw new IllegalArgumentException("Debe encontrarse en el rango entre 100 y 3.000 mililitros");
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0){
            this.stock = stock;
        } else {
            throw new IllegalArgumentException("Debe ser un valor mayor que cero.");
        }
    }

    //Metodo creado para ayudar la venta
    public void venderBebidaActual(int unidades){
        int venta = unidades;
        if (unidades<1){
            throw new VentaNoRealizada("ERROR: No se puede realizar una venta con numeros negativos");
        }
        if ((this.stock - unidades) < 0 ){
            throw new VentaNoRealizada("ERROR: No se puede realizar una venta con numeros negativos");
        }
        this.stock = this.stock - unidades;
        System.out.println("Venta realizada");
    }

}
