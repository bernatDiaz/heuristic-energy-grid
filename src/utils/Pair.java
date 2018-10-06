package utils;

public class Pair<First, Second>
{
    public Pair(final First first, final Second second)
    {
        this.first = first;
        this.second = second;
    }

    public Pair<First, Second> makePair(First first, Second second){
        this.first = first;
        this.second = second;
        return this;
    }

    public final First first()
    {
        return first;
    }

    public final Second second()
    {
        return second;
    }

    private First first;
    private Second second;
}
