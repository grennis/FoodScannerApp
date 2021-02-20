import { getLevelName, getLevelIcon, getLevelColor, getSummary } from '../data/ingredientsUI';
import { LEVEL_MILD, LEVEL_MODERATE, LEVEL_NONE, LEVEL_SEVERE } from "../data/ingredients";

test('getInfo', () => {
  expect(getLevelName(LEVEL_MODERATE)).toBe('Moderate');
  expect(getLevelName(-1)).toBe('?');
  expect(getLevelIcon(LEVEL_NONE)).toBe('md-thumbs-up');
  expect(getLevelColor(LEVEL_SEVERE)).toBe('#BB0000');
});

test('summarySevere', () => {
  const summary = getSummary({
    ingredients: [
      { text: "tomato", level: LEVEL_SEVERE },
      { text: "pinto bean", level: LEVEL_MILD },
      { text: "something", level: LEVEL_NONE }
    ]
  });

  expect(summary.icon).toBe('ios-warning');
  expect(summary.iconColor).toBe('#BB0000');
  expect(summary.text).toBe("Contains severe and mild ingredients.");
});

test('summaryMild', () => {
  const summary = getSummary({
    ingredients: [
      { text: "tomato", level: LEVEL_NONE },
      { text: "pinto bean", level: LEVEL_MILD },
      { text: "something", level: LEVEL_NONE }
    ]
  });

  expect(summary.icon).toBe('ios-warning');
  expect(summary.iconColor).toBe('#999900');
  expect(summary.text).toBe("Contains mild ingredients.");
});

test('summaryNone', () => {
  const summary = getSummary({
    ingredients: []
  });

  expect(summary.icon).toBeUndefined();
  expect(summary.iconColor).toBeUndefined();
  expect(summary.text).toBe('No ingredients listed.');
});

