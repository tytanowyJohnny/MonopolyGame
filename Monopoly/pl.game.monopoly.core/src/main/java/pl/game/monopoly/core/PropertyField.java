package pl.game.monopoly.core;

import org.javatuples.Pair;

/**
 * Klasa przechowuje informacje o polach które można nabyć i budować na nich domki
 */
public class PropertyField extends Field {
    private final int price;
    private String name;

    private int houses;
    private boolean hotel;

    public PropertyField(int id, int price, String name) {
        super(id);
        this.price = price;
        this.name = name;

    }

    /**
     * Metoda obliczająca czynsz
     *
     * @param houses         liczba domków
     * @param hotel          czy jest hotel?
     * @param hasAllProvince czy gracz posiada wszystkie pola w prowincji?
     * @return cena czynszu
     */
    public int calculateRent(int houses, boolean hotel, boolean hasAllProvince) {
        int tmpProv = hasAllProvince ? 1 : 2;
        if (hotel) {
            return (int) (((this.price * 0.1) + this.price * 5) * tmpProv);
        } else {
            return (int) (((this.price * 0.1) + (houses * this.price * 0.5)) * tmpProv);
        }
    }

    /**
     * Metoda licząca cenę domku
     *
     * @return Cena jednego domku
     */
    public int calculateHousePrice() {
        return (int) (price * 0.8);
    }

    /**
     * Metoda służąca do dodania domu do pola
     *
     * @param numberOfHouses Liczba domów do dodania
     * @return Para String - Komunikat, Boolean - czy udało się dodać
     */
    public Pair<String, Boolean> addHouse(int numberOfHouses) {
        if (this.houses == 4)
            return new Pair<>("Masz już maksymalną ilość domów! Kup hotel.", false);
        else if (this.houses + numberOfHouses > 4)
            return new Pair<>("Chcesz dodać za dużo domów! Możesz mieć maksymalnie 4!", false);
        else {
            this.houses += numberOfHouses;
            return new Pair<>("Dodano " + numberOfHouses + " domy.", true);
        }
    }


    /**
     * Metoda do dodawania hotelu do pola
     *
     * @return Para Komunikat + czy dodano hotel
     */
    public Pair<String, Boolean> buildHotel() {
        if (this.hotel)
            return new Pair<>("Już masz hotel!", false);
        else {
            this.hotel = true;
            return new Pair<>("Dodano hotel!", true);
        }
    }


    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getHouses() {
        return houses;
    }

    public boolean isHotel() {
        return hotel;
    }
}
