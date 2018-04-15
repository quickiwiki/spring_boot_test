package long_polling.server1;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;

@Data
@AllArgsConstructor
public class DataItem 
{
	private double x = 0.0;
	private double y = 0.0;

	static public double random()
	{
        double min = 0.01;
        double max = 0.99;
        double total = 100.0;
        
        return Math.round(new Random().nextFloat() * (max - min) * total) / total;
	}
}
