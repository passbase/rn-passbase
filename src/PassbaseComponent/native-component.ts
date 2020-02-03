import React from 'react'
import { requireNativeComponent, ViewProps } from 'react-native'

export type NativeProps = ViewProps

export const NativeComponent: React.ComponentClass<NativeProps> = requireNativeComponent(
  'RNPassbaseComponent'
)
