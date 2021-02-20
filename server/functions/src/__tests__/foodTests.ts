import { transformResult, processProduct } from '../scan_processor';

test('transform properties', () => {
  const food = {
    foodId: '1',
    brand: 'brand',
    label: 'label',
    image: 'image',
    foodContentsLabel: ''
  };

  const result = transformResult(food);

  expect(result.id).toBe('1');
  expect(result.brand).toBe('brand');
  expect(result.label).toBe('label');
  expect(result.image).toBe('image');
});

test('transform ingredients', () => {
  const food = {
    foodContentsLabel: 'a b; c, d; e, f. g; h (i) j'
  };

  const result = transformResult(food);

  expect(result.ingredients).toStrictEqual(['a b', 'c, d', 'e, f. g', 'h', 'i', 'j']);
});

test('process null', () => {
  expect(processProduct("1", null)).toBeNull();
});

test('process empty', () => {
  expect(processProduct("1", {
    hints: [ null ]
  })).toBeNull();
});

test('process product', () => {
  const result = processProduct("1", {
    hints: [{
      food: {
        brand: 'brand',
        label: 'label'
      }
    }]
  });

  expect(result!.brand).toEqual('brand');
  expect(result!.label).toEqual('label');
});
