package bisection;
import java.util.ArrayList;

public class Main {
	
	public static ArrayList<ArrayList<Integer>> getClasters(ArrayList<ArrayList<Double>> arr, int elementsInClaster)
	{
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		
		int SIZE = arr.size();
		// Построение матрицы достижимости		
		ArrayList<ArrayList<Boolean>> arrD = new ArrayList<ArrayList<Boolean>>();
		
		for(int i=0; i <SIZE; ++i)
		{
			ArrayList<Boolean> tmp = new ArrayList<Boolean>();
			for(int j=0; j<SIZE; ++j)
			{				
				if (arr.get(i).get(j) > 0.001)
					tmp.add(true);
				else
					tmp.add(false);
			}
			arrD.add(tmp);
		}
		// Постреоние матрицы переходов
		
		for(int i=0; i <SIZE; ++i)
		{
			//ArrayList<Boolean> tmp = arrD.get(i);
			for(int j=0; j<SIZE; ++j)
			{				
				if (arrD.get(i).get(j) == true)
				{
					
					for(int k=0; k< SIZE; ++k)
					{
						arrD.get(i).set(k , (arrD.get(j).get(k) || arrD.get(i).get(k))); 	
						arrD.get(k).set(i , (arrD.get(j).get(k) || arrD.get(i).get(k))); 	
					}
				}
				
			}			
		}
		
		// Составляем список независимых графов
		for(int i=0; i <SIZE; ++i)
		{			
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for(int j=0; j<SIZE; ++j)
			{				
				if (arrD.get(i).get(j) == true)
					tmp.add(j);
			}
			boolean flag = true;
			
			for(int k=0; k< res.size(); ++k)
			{				
				flag = true;
				if(res.get(k).get(0) == tmp.get(0)) // т.к. граф не ориентирован - то прокнет;)
				{
					flag = false;
					break;
				}
			}
			
			if (flag == true)
				res.add(tmp);
			
		}
		
		// проверка количества вершин в каждом независимом графе
		for(int i=0; i<res.size(); ++i)
		{
			// если количество вершин превышает порог, разбиваем неугодный граф на два и уходим в рекурсию. ми-ми-ми^^			
			if (res.get(i).size() > elementsInClaster) 
			{
				ArrayList<ArrayList<Double>> arr2 = new ArrayList<ArrayList<Double>>();
				
				// получаем инфу о весах дуг неугодного графа
				for(int j=0; j<res.get(i).size(); ++j)				
					arr2.add(arr.get(res.get(i).get(j)));
				
				Double min = 1.0; 
				// Ищем самую маленькую, но не нулевую связь и уничтожаем ее 
				for(int k=0; k<arr2.size(); ++k)
				{
					for(int l=0; l<SIZE; ++l)
					{
						if(arr2.get(k).get(l) <= min && arr2.get(k).get(l) > 0.00001)
							min = arr2.get(k).get(l);
					}						
				}
				
				for(int k=0; k< res.get(i).size(); ++k)
				{					
					for(int l=0; l<SIZE; ++l)
					{
						
						int qwer = res.get(i).get(k);
						if(res.get(i).get(k) == l)
							continue;
						Double qwe = arr.get(res.get(i).get(k)).get(l); // так надежней, чем напрямую вставлять в if
						if (qwe - min < 0.00001)
							arr.get(res.get(i).get(k)).set(l, 0.0);								
					}		
				}
				
				return getClasters(arr, elementsInClaster);													
			}
		}
					
			
		return res;			
		
		
	}

	public static void main(String[] args) 
	{
		int SIZE = 10;
		
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Double>> arr = new ArrayList<ArrayList<Double>>();
		
		// инициализация 0
		for(int i=0; i <SIZE; ++i)
		{
			ArrayList<Double> tmp = new ArrayList<Double>();
			for(int j=0; j<SIZE; ++j)
			{				
				tmp.add(0.0);				
			}
			arr.add(tmp);
		}
				
		// Инициализация согласно тестовой задаче
		for(int i=0; i <SIZE; ++i)
		{			
			for(int j=0; j<SIZE; ++j)
			{				
				if(i == j)
					arr.get(i).set(j, 1.0);
				
				if (i == 0 && j == 1 || i == 1 && j == 0)
					arr.get(i).set(j, 0.1);
				
				if (i == 1 && j == 2 || i == 2 && j == 1)
					arr.get(i).set(j, 0.3);
				
				if (i == 3 && j == 6 || i == 6 && j == 3)
					arr.get(i).set(j, 0.5);
				
				if (i == 4 && j == 7 || i == 7 && j == 4)
					arr.get(i).set(j, 0.2);
				
				if (i == 4 && j == 8 || i == 8 && j == 4)
					arr.get(i).set(j, 0.8);
				
				if (i == 5 && j == 7 || i == 7 && j == 5)
					arr.get(i).set(j, 0.7);
				
				if (i == 5 && j == 8 || i == 8 && j == 5)
					arr.get(i).set(j, 0.6);
				
				if (i == 7 && j == 8 || i == 8 && j == 7)
					arr.get(i).set(j, 0.4);
			}
			
		}
		
		int i=0;
		++i;
		ArrayList<ArrayList<Integer>> res = getClasters(arr, 3);
		
		++i;
		
		
		
		
		
		
		

	}

}
