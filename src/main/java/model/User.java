package model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public abstract class User implements Serializable, Comparable<User> {

    private String name;
    private int nif;
    private String email;
    private String address;
    private String hashedPassword;
    private LocalDate birthday;
    private double rating;
    private List<Aluguer> rents;
    private List<Notification> pendingTasks;
    private List<Double> classificacoes;

    private static float DEFAULT_RATING = 50;
    private static String DEFAULT_NAME = "undefined";
    private static String DEFAULT_EMAIL = "undefined";
    private static String DEFAULT_PASSWORD = "password";
    private static String DEFAULT_ADDRESS = "undefined";

    public static Map<String, Comparator<User>> userComparators;

    static {
        userComparators = new HashMap<>();
        userComparators.put("performedRents", new CompareByRents());
        userComparators.put("travelledKms", new CompareByKms());
    }

    /**
     * Função que aplica 'hash' à password de um usuário
     *
     * @param password Palavra-passe a 'hashear'
     * @return Palavra-passe 'hasheada'
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String ret = no.toString(16);
            while (ret.length() < 32) {
                ret = "0" + ret;
            }
            return ret;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm " + e);
            return null;
        }
    }

    /**
     * Construtor parametrizado
     *
     * @param p Objeto 'usuário'
     */
    public User(User p) {
        this.name = p.getName();
        this.nif = p.getNif();
        this.email = p.getEmail();
        this.address = p.getAddress();
        this.hashedPassword = p.getHashedPassword();
        this.birthday = p.getBirthday();
        this.rating = p.getRating();
        this.rents = p.getRents();
        this.pendingTasks = p.getPendingTasks();
        this.classificacoes = p.getClassificacoes();
    }

    /**
     * Construtor parametrizado
     *
     * @param name     Nome de usuário
     * @param nif      NIF do usuário
     * @param email    Email do usuário
     * @param address  Morada do usuário
     * @param password Palavra-passe do usuário
     */
    public User(String name,
                int nif,
                String email,
                String address,
                String password) {
        this.name = name;
        this.nif = nif;
        this.email = email;
        this.hashedPassword = User.hashPassword(password);
        this.address = address;
        this.birthday = LocalDate.now();
        this.rating = DEFAULT_RATING;
        this.rents = new ArrayList<>();
        this.pendingTasks = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
    }

    /**
     * Construtor parametrizado
     *
     * @param name    Nome do usuário
     * @param nif     NIF do usuário
     * @param email   Email do usuário
     * @param address Morada do usuário
     */
    public User(String name,
                int nif,
                String email,
                String address) {
        this.name = name;
        this.nif = nif;
        this.email = email;
        this.address = address;
        this.hashedPassword = hashPassword(DEFAULT_PASSWORD);
        this.birthday = LocalDate.now();
        this.rating = DEFAULT_RATING;
        this.rents = new ArrayList<>();
        this.pendingTasks = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
    }

    /**
     * Permite obter o nome de usuário
     *
     * @return Nome de usuário
     */
    public String getName() {
        return this.name;
    }

    /**
     * Permite obter o NIF do usuário
     *
     * @return NIF do usuário
     */
    public int getNif() {
        return this.nif;
    }

    /**
     * Permite obter o email do usuário
     *
     * @return Email do usuário
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Permite obter a morada do usuário
     *
     * @return Morada do usuário
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Permite obter a password 'hasheada' de um usuário
     *
     * @return Password 'hasheada'
     */
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * Permite obter a data de nascimento de um usuário
     *
     * @return Data de nascimento do usuário
     */
    public LocalDate getBirthday() {
        return this.birthday;
    }

    /**
     * Permite obter o 'rating' de um usuário
     *
     * @return Rating do usuário
     */
    public double getRating() {
        return this.rating;
    }

    /**
     * Permite obter a lista de alugueres de um usuário
     *
     * @return Lista de alugueres de um usuário
     */
    public List<Aluguer> getRents() {
        return this.rents.stream().map(Aluguer::clone).collect(Collectors.toList());
    }

    public List<Notification> getPendingTasks() {
        return this.pendingTasks.stream().map(Notification::clone).collect(Collectors.toList());
    }

    public List<Double> getClassificacoes() {
        return this.classificacoes.stream().collect(Collectors.toList());
    }

    /**
     * Permite definir o nome de um usuário
     *
     * @param name Nome a definir
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Permite definir o email de um usuário
     *
     * @param email Email a definir
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Permite definir o NIF de um usuário
     *
     * @param nif NIF a definir
     */
    public void setNif(int nif) {
        this.nif = nif;
    }

    /**
     * Permite definir a password 'hasheada' de um usuário
     *
     * @param hashedPassword Password a definir
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Permite definir a morada de um usuário
     *
     * @param address Morada a definir
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Permite definir a data de nascimento de um usuário
     *
     * @param d Data de nascimento a definir
     */
    public void setBirthday(LocalDate d) {
        this.birthday = d;
    }

    /**
     * Permite definir o rating de um usuário
     *
     * @param rating Rating a definir
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Permite definir a lista de alugueres de um usuário
     *
     * @param l Lista de alugueres a definir
     */
    public void setRents(List<Aluguer> l) {
        this.rents = l.stream().map(Aluguer::clone).collect(Collectors.toList());
    }

    public void setClassificacoes(List<Double> l) {
        this.classificacoes.addAll(l);
    }

    public void setPendingTasks(List<Notification> l) {
        this.pendingTasks = l.stream().map(Notification::clone).collect(Collectors.toList());
    }

    public void addAluguer(Aluguer a) {
        this.rents.add(a.clone());
    }

    public void addRating(double rating) {
        this.classificacoes.add(rating);
        this.rating = this.calculateRating();
    }

    public void addNotification(Notification n) {
        this.pendingTasks.add(n.clone());
    }

    /**
     * Permite comparar dois usuários
     *
     * @param u Usuário a comparar
     * @return Inteiro conforme o resultado da comparação (similar ao 'compareTo' de strings)
     */
    public int compareTo(User u) {
        return this.email.compareTo(u.getEmail());
    }

    /**
     * Permite comparar dois usuários
     *
     * @param object Usuário a comparar
     * @return 'true' se forem iguais ou 'false' caso contrário
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        User that = (User) object;
        return this.name.equals(that.getName()) &&
                this.nif == that.getNif() &&
                this.email.equals(that.getEmail()) &&
                this.hashedPassword.equals(that.getHashedPassword()) &&
                this.birthday.equals(that.getBirthday()) &&
                Double.compare(this.rating, that.rating) == 0 &&
                this.rents.equals(that.getRents()) &&
                this.pendingTasks.equals(that.getPendingTasks()) &&
                this.classificacoes.equals(that.getClassificacoes());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + nif;
        result = 31 * result + email.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + hashedPassword.hashCode();
        result = 31 * result + birthday.hashCode();
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + rents.hashCode();
        result = 31 * result + pendingTasks.hashCode();
        result = 31 * result + classificacoes.hashCode();
        return result;
    }

    /**
     * Permite obter uma representação textual dos objetos do tipo 'User'
     *
     * @return Representação textual do objeto do tipo 'User'
     */
    @Override
    public String toString() {
        return "User{" +
                "name= " + this.name +
                ", nif= " + this.nif +
                ", email= " + this.email +
                ", address= " + this.address +
                ", hashedPassword= " + this.hashedPassword +
                ", birthday= " + this.birthday +
                ", rating= " + this.rating +
                ", rents= " + this.rents +
                ", pendingTasks= " + this.pendingTasks +
                ", classificacoes= " + this.classificacoes +
                '}';
    }

    /**
     * Permite obter uma cópia de um objeto do tipo 'User'
     *
     * @return Cópia do objeto
     */
    public abstract User clone();

    public double calculateRating() {
        double sum = 0;
        for (double i : this.classificacoes)
            sum += i;
        return (sum / this.classificacoes.size());
    }

    public int performedRents() {
        return this.rents.size();
    }

    public double travelledKms() {
        double totalKm = 0;
        for (Aluguer l : this.rents)
            totalKm += l.getOrigin().distance(l.getDestination());
        return totalKm;
    }
}
