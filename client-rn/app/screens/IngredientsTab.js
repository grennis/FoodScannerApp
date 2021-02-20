import React from 'react';
import { View, SectionList, Text } from 'react-native';
import { GlobalStyles, headerTitleBarStyle } from '../components/styles';
import { createStackNavigator } from '@react-navigation/stack';
import { ingredientLevels } from "../data/ingredients";
import { getLevelColor, getLevelName } from "../data/ingredientsUI";

const Item = (item) => (
  <View>
    <Text style={{ color: getLevelColor(item.section.level), paddingTop: 2, paddingBottom: 2 }}>{item.item}</Text>
  </View>
);

const SectionHeader = ({ section }) => {
  console.info(JSON.stringify(section));

  return (
  <View>
    <Text style={{ color: '#000000', fontSize: 20, paddingTop: 8, paddingBottom: 8 }}>{getLevelName(section.level).toUpperCase()}</Text>
  </View>
)};

function Ingredients() {
  let sections = ingredientLevels
    .filter(i => i.ingredients.length > 0)
    .map(section => ({ ...section, data: section.ingredients }))
    .reverse();

  return (
    <View style={GlobalStyles.tab}>
      <SectionList style={{paddingStart: 16, width: '100%'}} sections={sections}
         keyExtractor={(item, index) => index}
         renderItem={Item}
         renderSectionHeader={SectionHeader} />
    </View>
  );
}

export function IngredientsTab() {
  let Stack = createStackNavigator();

  return <Stack.Navigator screenOptions={headerTitleBarStyle}>
    <Stack.Screen name="Ingredients" component={Ingredients} options={{title: 'Ingredients'}}/>
  </Stack.Navigator>
}
