import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) {
		// Scan for input. Save the size of the array as N and the size of each
		// window as K.
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		StringTokenizer token = new StringTokenizer(input);
		int N = Integer.parseInt((String) token.nextElement());
		int K = Integer.parseInt((String) token.nextElement());
		input = sc.nextLine();
		token = new StringTokenizer(input);
		int count = token.countTokens();
		boolean isNonDecreasing[] = new boolean[count - 1];
		boolean isNonIncreasing[] = new boolean[count - 1];
		int previous = Integer.parseInt((String) token.nextElement());

		if (K > 1) {
			int current;

			// Set up boolean arrays. Each pair of adjacent elements of the
			// input array are compared.
			// If the elements match the quality defined by the array
			// (non-decreasing or non-increasing)
			// then true is added to the boolean array. Otherwise, false is
			// added to the boolean array.
			for (int i = 0; i < count - 1; i++) {
				current = Integer.parseInt((String) token.nextElement());
				isNonDecreasing[i] = (previous <= current);
				isNonIncreasing[i] = (previous >= current);
				previous = current;
			}

			ArrayList<Integer> nonDecreasingClusters = new ArrayList<Integer>();
			ArrayList<Integer> nonIncreasingClusters = new ArrayList<Integer>();

			int clusterCount = 0;

			// In this program we are considering the clusters of trues in the
			// boolean array.
			// A cluster will be defined as a group of true elements such that
			// there are no false
			// elements between any true elements of that group.
			// Here we count clusters of true booleans for the first window of
			// size K and add
			// that number to the relevant ArrayList.
			for (int i = 0; i < K - 1; i++) {
				if (clusterCount == 0 && isNonDecreasing[i] == false) {
				} else {
					if (isNonDecreasing[i] == false) {
						nonDecreasingClusters.add(clusterCount);
						clusterCount = 0;
					} else {
						clusterCount++;
					}
				}
			}
			if (clusterCount != 0) {
				nonDecreasingClusters.add(clusterCount);
			}

			clusterCount = 0;

			for (int i = 0; i < K - 1; i++) {
				if (clusterCount == 0 && isNonIncreasing[i] == false) {
				} else {
					if (isNonIncreasing[i] == false) {
						nonIncreasingClusters.add(clusterCount);
						clusterCount = 0;
					} else {
						clusterCount++;
					}
				}
			}
			if (clusterCount != 0) {
				nonIncreasingClusters.add(clusterCount);
			}

			// Based on the cluster counts of the ArrayLists, add the amount of
			// NonIncreasing/NonDecreasing subranges that each cluster would
			// contribute
			// for the first window of size K
			long nonDecreasingCount = 0;
			long nonIncreasingCount = 0;
			int holder;
			long net;

			for (int i = 0; i < nonIncreasingClusters.size(); i++) {
				nonIncreasingCount += fromClusterGetCount(nonIncreasingClusters
						.get(i));
			}
			for (int i = 0; i < nonDecreasingClusters.size(); i++) {
				nonDecreasingCount += fromClusterGetCount(nonDecreasingClusters
						.get(i));
			}
			net = nonDecreasingCount - nonIncreasingCount;
			System.out.println(net);

			// Now we shift the window of size K over the rest of the input
			// array. Each time we
			// shift it, we consider the boundary elements on the left and the
			// right of the window.
			// Based on the elements being added to and removed from the window,
			// you can calculate
			// the net change from the last window's nonDecreasingCount -
			// nonIncreasingCount. Then
			// you can set up the Arraylists for the next window's calculation.
			for (int i = 1; i < N - K + 1; i++) {

				nonDecreasingCount = 0;
				nonIncreasingCount = 0;
				if (isNonIncreasing[i - 1] == true) {
					if (isNonIncreasing[i] == true) {
						holder = nonIncreasingClusters.get(0);
						nonIncreasingClusters.set(0, holder - 1);
						net = net + fromClusterGetCount(holder)
								- fromClusterGetCount(holder - 1);
					} else {
						nonIncreasingClusters.remove(0);
						net += 1;
					}
				}

				if (isNonDecreasing[i - 1] == true) {
					if (isNonDecreasing[i] == true) {
						holder = nonDecreasingClusters.get(0);
						nonDecreasingClusters.set(0, holder - 1);
						net = net - fromClusterGetCount(holder)
								+ fromClusterGetCount(holder - 1);
					} else {
						nonDecreasingClusters.remove(0);
						net -= 1;
					}
				}

				if (isNonIncreasing[i + K - 2] == true) {
					if (isNonIncreasing[i + K - 3] == true) {
						holder = nonIncreasingClusters
								.get(nonIncreasingClusters.size() - 1);
						nonIncreasingClusters.set(
								nonIncreasingClusters.size() - 1, holder + 1);
						net = net + fromClusterGetCount(holder)
								- fromClusterGetCount(holder + 1);
					} else {
						nonIncreasingClusters.add(1);
						net -= 1;
					}
				}
				if (isNonDecreasing[i + K - 2] == true) {
					if (isNonDecreasing[i + K - 3] == true) {
						holder = nonDecreasingClusters
								.get(nonDecreasingClusters.size() - 1);
						nonDecreasingClusters.set(
								nonDecreasingClusters.size() - 1, holder + 1);
						net = net - fromClusterGetCount(holder)
								+ fromClusterGetCount(holder + 1);
					} else {
						nonDecreasingClusters.add(1);
						net += 1;
					}
				}

				System.out.println(net);
			}
		}
		// When K is 1, there are 0 non-decreasing and non-increasing subranges
		else {
			for (int h = 0; h < N; h++) {
				System.out.println(0);
			}
		}
	}

	// Based on the size of the cluster, this method returns the actual count
	// of non-decreasing or non-increasing subranges.
	public static long fromClusterGetCount(long clusterSize) {
		clusterSize++;
		return ((clusterSize * clusterSize) - clusterSize) / 2;
	}

}
