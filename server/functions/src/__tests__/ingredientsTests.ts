import {findIn, getLevel, processIngredients} from '../ingredient_processor';
import {LEVEL_MILD, LEVEL_NONE, LEVEL_SEVERE} from '../ingredients';

test('findIn', () => {
  expect(findIn("test", [ "ABC", "XYZ" ])).toBeFalsy();
  expect(findIn("test", [ "ABC TEST", "XYZ" ])).toBeTruthy();
  expect(findIn("abc", [ "ABC TEST", "XYZ" ])).toBeTruthy();
  expect(findIn("test", [ "atest" ])).toBeFalsy();
  expect(findIn("testa testb", [ "testa" ])).toBeTruthy();
  expect(findIn("test", [ "testa test" ])).toBeTruthy();
  expect(findIn("corn flour", [ "wheat flour" ])).toBeFalsy();
  expect(findIn("citric acid", [ "test acid" ])).toBeFalsy();
});

test('level', () => {
  expect(getLevel("corn")).toBe(LEVEL_NONE);
  expect(getLevel("tomato")).toBe(LEVEL_SEVERE);
  expect(getLevel("pinto bean")).toBe(LEVEL_MILD);
});

test('ingredients', () => {
  const results = processIngredients([ "pinto bean", "nothing" ]);

  expect(results.find(i => i.text === 'pinto bean').level).toBe(LEVEL_MILD);
});
