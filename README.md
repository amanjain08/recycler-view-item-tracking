# recycler-view-item-tracking

Problem Statement:
Use RxJava to implement tracking for visible items in a RecyclerView.

Display a RecyclerView with enough items so that it is scrollable to 3 pages. Whenever a RV item
is visible to user (even partially) for 300ms, log its position to logcat.

Example (divided into 3 scenarios):

1. Let's say items 1, 2 and 3 are visible in RV for 3 seconds.

   > 1, 2 and 3 are tracked once the first 300ms is elapsed

2. Now user scrolls the list, making items 4, 5 and 6 visible for less than 300ms, consequently making 1, 2 and 3 items go off-screen for that much time.

After that time is elapsed, user scrolls back to the top, making the initial 1, 2 and 3 items visible again.

   > tracking for 4, 5 and 6 items is skipped

3. User keeps 1, 2 and 3 visible for a second.

   > 1, 2 and 3 are tracked once the first 300ms is elapsed
