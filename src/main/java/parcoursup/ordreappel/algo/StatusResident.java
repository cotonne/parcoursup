package parcoursup.ordreappel.algo;

public enum StatusResident implements StatusAvecPriorite {
    RESIDENT, NON_RESIDENT;

    @Override
    public StatusAvecPriorite nonPrioritaire() {
        return RESIDENT;
    }
}
