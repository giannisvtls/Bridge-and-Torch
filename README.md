# Bridge-and-Torch

Java project for the Artificial Intelligence course at AUEB. 

<h2>Bridge and Torch Game Description</h2>

The bridge and torch problem is a logic puzzle that deals with N people, a bridge and a torch. It is in the category of river crossing puzzles, where a family must move across a river, with some constraints.

<h3>Constraints</h3>
<ol>
  <li>The bridge can hold up to two people at a time
  <li>Only one torch is available and it has to be used for a person or a couple to cross the bridge
  <li>Each persons moves at a different pace, but the pace is stable no matter the side in which he starts.
  <li>When two people cross the bridge together, they must move at the slower person's pace
</ol>

<h3>How to</h3>
<ul>
<li>At the start of the game the user has to provide:
  <ol>
    <li>The number of people that need to cross
    <li>The pace in which each person moves
    <li>The time the torch will stay lit.
  </ol>
 <li>Once the user provides all the information, the program will determine if it is possible for all the family members to cross using the A* algorithm to provide the optimal solution.
 <li>In the end it also displays the optimal order in which each family member(or members) will cross.
