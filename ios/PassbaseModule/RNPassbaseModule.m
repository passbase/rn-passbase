#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNPassbaseSDK, NSObject)

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

// todo: keep only start verification method on main queue
- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"onError", @"onFinish", @"onStart", @"onSubmitted"];
}

- (NSDictionary *)constantsToExport
{
  return @{
      @"ERROR_INITIALIZING_PASSBASE": @"error_initializing_passbase_sdk",
      @"INITIALZE_PASSBASE_TO_START_VERIFICATION": @"error_starting_verification",
      @"ERROR_START_VERIFICATION": @"initialize_passbase_sdk_before_trying_verification",
      @"VERIFICATION_CANCELLED": @"verification_cancelled",
      @"REQUIRED_OPTION_API_KEY_MISSING": @"required_option_api_key_is_missing",
      @"REQUIRED_OPTION_CUSTOMER_PAYLOAD_MISSING": @"required_option_customer_payload_is_missing",
      @"SUCCESS": @"success",
      @"ERROR": @"error",
  };
}

RCT_EXTERN_METHOD(show:(NSString *)message)

RCT_EXTERN_METHOD(setPrefillUserEmail:(NSString *)email)

RCT_EXTERN_METHOD(initialize:(NSString *)publishableApiKey :(NSString *)customerPayload resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(initWithCB:(NSString *)publishableApiKey customerPayload:(NSString *)customerPayload onSuccess:(RCTResponseSenderBlock)onSuccess onFailure:(RCTResponseSenderBlock)onFailure)

RCT_EXTERN_METHOD(startVerification:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(startVerificationWithCB:(RCTResponseSenderBlock)onSuccess onFailure:(RCTResponseSenderBlock)onFailure)

@end
